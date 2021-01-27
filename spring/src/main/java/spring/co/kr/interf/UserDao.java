package spring.co.kr.interf;

import java.util.List;

import spring.co.kr.dao.User;

public interface UserDao {

	void add(User user);
	User get(String id);
	List<User> getAll();
	void deleteAll();
	int getCount();
	void update(User user);
}
