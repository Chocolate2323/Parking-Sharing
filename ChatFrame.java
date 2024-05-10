package com.example.rabbitdemo1.chatUi;

import com.example.rabbitdemo1.ChatMsg.ChatMsg;
import com.example.rabbitdemo1.consumer.Consumer;

import com.example.rabbitdemo1.producer.Producer;
import com.example.rabbitdemo1.producer.Producer1;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatFrame {
    @Autowired
    private Producer1 producer;

    @Autowired
    private RabbitTemplate rabbit;

    public static String username;
    private JTextArea chatArea;
    private JTextField inputField;
    private static final JFrame frame = new JFrame("聊天室");

    private static final Logger logger = LoggerFactory.getLogger(ChatFrame.class);


    public ChatFrame() {
        //frame = new JFrame("聊天室-user1");
        logger.debug("ChatFrame init...");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        JButton sendButton = new JButton("发送");
        sendButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        inputField.setPreferredSize(new Dimension(300, 30));
        sendButton.setPreferredSize(new Dimension(80, 30));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // 创建文件选择按钮
        JButton fileChooserButton = new JButton("浏览文件");
        fileChooserButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        fileChooserButton.setPreferredSize(new Dimension(100, 30));
        inputPanel.add(fileChooserButton, BorderLayout.WEST);

        sendButton.addActionListener(e -> {
            try {
                sendMessage();
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        });

        fileChooserButton.addActionListener(e -> {
            // 创建文件选择对话框
            JFileChooser fileChooser = new JFileChooser();

            // 显示文件选择对话框并等待用户选择文件
            int result = fileChooser.showOpenDialog(frame);

            // 如果用户选择了文件，处理所选文件
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    sendFile(selectedFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.setVisible(false);
    }

    public static void setChatFrameVisible()
    {
        frame.setVisible(true);
    }

    public static void setTitle(String username) {
        frame.setTitle("聊天室-" + username);
        ChatFrame.username = username;
    }

    private void sendFile(File selectedFile) throws IOException {
        byte[] fileData = Files.readAllBytes(selectedFile.toPath());
        producer.sendFile(fileData);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = currentDateTime.format(formatter);

        chatArea.append("<" + currentTime + " 发送文件> " + selectedFile.getName() + "\n");

    }

    private void sendMessage() throws JsonProcessingException {
        // 实现消息发送逻辑
        String message = inputField.getText();

        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 创建日期时间格式化对象，以便按需要格式化日期时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化当前时间
        String currentTime = currentDateTime.format(formatter);

        ChatMsg chatMsg = new ChatMsg(username, "user2", message, currentTime);

        ObjectMapper objectMapper = new ObjectMapper();

        rabbit.convertAndSend("topic_exchange", "1wyx.msg", objectMapper.writeValueAsString(chatMsg));

        //producer.send(message);

        chatArea.append("<" + currentTime + " 发送> " + message + "\n");

        inputField.setText("");
    }
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(),
//            exchange = @Exchange(value = "wyx_dirent")
//    ))
    @RabbitListener(queues = "Wan_Yixiang")
    private void receiveMessage(String message) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = currentDateTime.format(formatter);

        chatArea.append("<" + currentTime + " 接收> " + message + "\n");
    }

    @RabbitListener(queues = "Wan_YixiangF")
    private void receiveFile(byte[] fileData) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = currentDateTime.format(formatter);

        try {
            // 指定文件存储路径
            String filePath = "D:\\chatTmp\\file.tmp";

            // 创建一个新文件并写入字节数组
            File receivedFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(receivedFile);
            fos.write(fileData);
            fos.close();

            chatArea.append("<" + currentTime + " 接收文件> " + filePath + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            // 处理文件保存错误
        }
    }
}

