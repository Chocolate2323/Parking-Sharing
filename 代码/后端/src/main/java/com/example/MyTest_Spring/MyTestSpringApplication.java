package com.example.MyTest_Spring;

import com.example.MyTest_Spring.controller.UserController;
import com.example.MyTest_Spring.entity.Users;
import com.example.MyTest_Spring.repository.UserRepository;
import com.example.MyTest_Spring.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


@Configuration
@SpringBootApplication(scanBasePackages = "com.example.MyTest_Spring")
public class MyTestSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTestSpringApplication.class, args);


//		UserRepository userRepository = new UserRepository();
//		UserService userService = new UserService(userRepository);
//		UserController userController = new UserController(userService);
//
//		// 调用控制器方法，处理用户请求
//		List<Users> users = userController.getAllUsers();
//		// 其他操作...
	}
//public static void main(String[] args) {
//	//使用jbdc进行驱动;
//	String RL="jdbc:sqlserver://localhost:1433;database=My_Test_Car;encrypt=true;trustServerCertificate=true";
//	String sqlStr="select *\r\n"
//			+ "from Users\r\n"
//			;
//	try {
//		//加载驱动包
//		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		System.out.println("启动成功");
//		String userName="Chocolate";
//		String userPwd="20031015";
//		//创建数据库连接
//		Connection dbConn= DriverManager.getConnection(RL,userName,userPwd);
//		System.out.println("链接成功");
//		//向数据库发送个东西
//		Statement stmt=dbConn.createStatement();
//
//		ResultSet rs=stmt.executeQuery(sqlStr);//执行数据库查询语句存在于sqlStr中
//		while(rs.next())
//		{
////
//			String  str=rs.getString("User_ID")+"   "+rs.getString("User_Name")+
//					"   "+rs.getString("Password")+"   "+rs.getString("Email")+
//					"   "+rs.getString("Phone");
//
//			System.out.println(str);
//
//		}
//		dbConn.close();
//	}catch(Exception e) {
//		e.printStackTrace(System.out);
//	}
//}
}
