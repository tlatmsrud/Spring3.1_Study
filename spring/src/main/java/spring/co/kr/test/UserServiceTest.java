package spring.co.kr.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import spring.co.kr.dao.MockUserDao;
import spring.co.kr.dao.User;
import spring.co.kr.dao.UserDaoJdbc;
import spring.co.kr.domain.Level;
import spring.co.kr.exception.TestUserServiceException;
import spring.co.kr.proxy.TransactionHandler;
import spring.co.kr.service.MockMailSender;
import spring.co.kr.service.TestUserService;
import spring.co.kr.service.UserServiceImpl;
import spring.co.kr.service.Interface.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDaoJdbc userDao;
	
	List<User> users;
	
	@Autowired
	MailSender mailSender;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Before
	public void setUp() {

		users = Arrays.asList(
				new User("sskssk","승경","p2",Level.BASIC,29,0,"tlatmsrud@naver.com"),
				new User("sskssk1","승경","p2",Level.SILVER,50,0,"tlatmsrud@naver.com"),
				new User("sskssk2","승경","p2",Level.SILVER,60,10-1,"tlatmsrud@naver.com"),
				new User("sskssk3","승경","p2",Level.GOLD,60,30,"tlatmsrud@naver.com"),
				new User("sskssk4","승경","p2",Level.GOLD,100,Integer.MAX_VALUE,"tlatmsrud@naver.com")
		);
	}
	
	@Test
	@DirtiesContext
	public void upgradeLevels() throws Exception {
		
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		
		MockUserDao mockUserDao = new MockUserDao(this.users);
		userServiceImpl.setUserDao(mockUserDao);
		
		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
		List<User> updated = mockUserDao.getUpdated();
		assertThat(updated.size(),is(2));
		checkUserAndLevel(updated.get(0),"sskssk1",Level.SILVER);
		checkUserAndLevel(updated.get(1),"sskssk3",Level.GOLD);
		
		List<String> request = mockMailSender.getRequests();
		assertThat(request.size(), is(2));
		assertThat(request.get(0), is(users.get(1).getEmail()));
		assertThat(request.get(1), is(users.get(3).getEmail()));
		
		for(int i =0;i<request.size();i++) {
			System.out.println(request.get(i));
		}
	}
	
	private void checkUserAndLevel(User user, String expectedId, Level expectedLevel) {
		assertThat(user.getId(),is(expectedId));
		assertThat(user.getLevel(),is(expectedLevel));
	}
	
	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
	
	@Test
	public void add() {
		
		userDao.deleteAll();
		 
		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null); //레벨 객체를 null로.
		
		userServiceImpl.add(userWithLevel);
		userServiceImpl.add(userWithoutLevel);
		
		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		assertThat(userWithLevel.getLevel(), is(userWithLevelRead.getLevel()));
		assertThat(userWithoutLevel.getLevel(), is(userWithoutLevelRead.getLevel()));
		
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		userDao.deleteAll();
		
		TestUserService testUserService = new TestUserService(users.get(0).getId());
		testUserService.setUserDao(userDao);
		testUserService.setMailSender(mailSender);
		
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(testUserService);
		txHandler.setTransactionManager(transactionManager);
		txHandler.setPattern("upgradeLevels");
		
		UserService txUserService = (UserService)Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[] {UserService.class}, 
				txHandler);

		for(User user : users) userDao.add(user);
		
		try {
			txUserService.upgradeLevels(); 
		}catch(TestUserServiceException e) {
			System.out.println(e.getMessage());
		}
		
		checkLevelUpgraded(users.get(1), false);
		
	
	}
	
	
	public void checkLevelUpgraded(User user, boolean bool) {
		Level level = user.getLevel(); // 최초 user 레벨.
		User afterDBUser = userDao.get(user.getId()); 
		Level DBLevel = afterDBUser.getLevel(); //DB에서 조회한 user 레벨.
		
		if(bool == true) { //true일 경우 level이 업그레이드 됐다면, 즉 레벨이 올랐다면 테스트 성공
			assertThat(level.nextLevel(), is(DBLevel));
		}else { //false 일 경우 level이 업그레이드 되지 않았다면, 즉 레벨이 똑같다면 성공
			assertThat(level, is(DBLevel));
		}
		
	}
	
	
}
