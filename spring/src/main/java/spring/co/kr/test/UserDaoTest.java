package spring.co.kr.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring.co.kr.dao.User;
import spring.co.kr.domain.Level;
import spring.co.kr.interf.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		this.user1 = new User("ssk","심승경","ispring",Level.BASIC, 1, 0,"tlatmsrud@naver.com");
		this.user2 = new User("ssk2","심승경2","ispring2",Level.SILVER, 55, 10,"tlatmsrud@naver.com");
		this.user3 = new User("ssk3","심승경2","ispring2",Level.GOLD, 100, 40,"tlatmsrud@naver.com");
		
		dao.add(user1);
		dao.add(user2);
		dao.add(user3);
	}
	
	@After
	public void end() {
		dao.deleteAll();
	}
	
	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getLevel(), is(user2.getLevel()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getLogin(), is(user2.getLogin()));
		assertThat(user1.getRecommend(), is(user2.getRecommend()));
	}

	@Test
	public void addAndGet() {
		User userget1 = dao.get(user1.getId());
		checkSameUser(userget1, user1);
	
		User userget2 = dao.get(user2.getId());
		checkSameUser(userget2, user2);
	
	}
	
	/*
	 * @Test public void update() { dao.deleteAll(); dao.add(user1);
	 * 
	 * user1.setName("승경"); user1.setPassword("hi"); user1.setLevel(Level.GOLD);
	 * user1.setLogin(1000); user1.setRecommend(999);
	 * 
	 * dao.update(user1); User user1update = dao.get(user1.getId());
	 * checkSameUser(user1, user1update); }
	 */
	
}
