package com.example.MyTest_Spring;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class test {
    @RequestMapping("/testSpringBoot")
    @ResponseBody
    public String testSpringBoot() {
        return "恭喜！测试成功！";
    }
}
