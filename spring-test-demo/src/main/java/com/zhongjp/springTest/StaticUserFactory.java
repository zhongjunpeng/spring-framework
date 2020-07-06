package com.zhongjp.springTest;

public class StaticUserFactory {

	public static User getUser() {
		return new User();
	}
}
