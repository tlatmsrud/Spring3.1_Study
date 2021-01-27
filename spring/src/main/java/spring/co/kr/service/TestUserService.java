package spring.co.kr.service;

import java.io.UnsupportedEncodingException;

import spring.co.kr.dao.User;
import spring.co.kr.exception.TestUserServiceException;

public class TestUserService extends UserServiceImpl {

	private String id;
	
	public TestUserService(String id) {
		this.id = id;
	}
	
	public void upgradeLevel(User user) throws UnsupportedEncodingException {
		if(user.getId().equals(this.id)) throw new TestUserServiceException();
		super.upgradeLevel(user);
	}
}
