package spring.co.kr.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDaoJdbc userDao = context.getBean("userDao", UserDaoJdbc.class);
		
		
		User user = new User();
		user.setId("11");
		user.setName("심승경");
		user.setPassword("심승경");
		
		userDao.add(user);
		
		System.out.println("db 등록 성공 : "+ userDao.getCount());
		
		userDao.deleteAll();
	}
}
