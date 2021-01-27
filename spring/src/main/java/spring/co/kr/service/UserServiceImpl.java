package spring.co.kr.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import spring.co.kr.dao.User;
import spring.co.kr.domain.Level;
import spring.co.kr.interf.UserDao;
import spring.co.kr.service.Interface.UserService;

public class UserServiceImpl implements UserService {

	UserDao userDao;
	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void upgradeLevels() throws Exception {

		try {
			
			upgradeLevelsInternal();

		} catch (RuntimeException e) {

			throw e;
		}
	}

	public void sendUpgradeEmail(User user) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("tlatmsrud@naver.com");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "으로 올랐습니다.");

		mailSender.send(mailMessage);

	}

	public void add(User user) {
		// insert 하려는 User의 Level이 null일 경우 BASIC 레벨을 세팅해주고 userDao.add 해준다.
		if (user.getLevel() == null) {
			user.setLevel(Level.BASIC);
		}
		userDao.add(user);
	}

	public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();

		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER:
			return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unkown Level:" + currentLevel);
		}
	}

	public void upgradeLevel(User user) throws UnsupportedEncodingException {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEmail(user);
	}
	
	public void upgradeLevelsInternal() throws UnsupportedEncodingException  {
		System.out.println(userDao);
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}
}
