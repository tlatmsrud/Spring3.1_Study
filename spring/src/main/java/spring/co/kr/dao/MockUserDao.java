package spring.co.kr.dao;

import java.util.ArrayList;
import java.util.List;

import spring.co.kr.interf.UserDao;

public class MockUserDao implements UserDao{

	private List<User> users;
	private List<User> updated = new ArrayList<User>();
	
	public MockUserDao(List<User> users) {
		this.users = users;
	}
	
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return this.users;
	}
	
	public List<User> getUpdated(){
		return this.updated;
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		updated.add(user);
	}
	

	
	public void add(User user) {
		throw new UnsupportedOperationException();
	}
	public User get(String id) {
		throw new UnsupportedOperationException();
	}
	public void deleteAll() {
		throw new UnsupportedOperationException();
	}
	public int getCount() {
		throw new UnsupportedOperationException();
	}

	
	
}
