package com.example.MyTest_Spring;

import com.example.MyTest_Spring.controller.UserController;
import com.example.MyTest_Spring.entity.Users;
import com.example.MyTest_Spring.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

public class MyTestSpringApplicationTests {

	@Mock
	private UserService userService;

	private UserController userController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		userController = new UserController(userService);
	}

	@Test
	public void testListAllUsers() {
		// 模拟 UserService 返回用户列表
		List<Users> expectedUsersList = Arrays.asList(
				new Users( 1,"User1","pass1","email1","num1",100,0),
				new Users( 2,"User2","pass2","email2","num2",100,0),
				new Users( 3,"User3","pass3","email3","num3",100,0)
		);
		when(userService.getAllUsers()).thenReturn(expectedUsersList);

		// 调用控制器方法，处理用户请求
		List<Users> actualUsersList = userController.getAllUsers();

		// 验证返回的结果与预期的数据匹配
		Assertions.assertEquals(expectedUsersList.size(), actualUsersList.size());
		for (int i = 0; i < expectedUsersList.size(); i++) {
			Users expectedUser = expectedUsersList.get(i);
			Users actualUser = actualUsersList.get(i);
			Assertions.assertEquals(expectedUser.getUserId(), actualUser.getUserId());
			Assertions.assertEquals(expectedUser.getUserName(), actualUser.getUserName());
			// 其他断言...
		}

		// 验证 UserService 的 getAllUsers 方法被调用了一次
		verify(userService, times(1)).getAllUsers();
	}

	// 可以继续编写其他测试方法
}
