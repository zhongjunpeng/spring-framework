package com.zhongjp.springTest;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
@Conditional(Person.class)
public class JavaConfig {

	@Bean
	public User user(){
		User user = new User();
		user.setUsername("zhangsan");
		user.setUid(1);
		user.setTel("1231313213");
		user.setPwd("78454545");
		user.setAddr("45454545");
		return user;
	}
}
