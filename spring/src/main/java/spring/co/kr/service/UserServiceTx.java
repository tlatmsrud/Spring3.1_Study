package spring.co.kr.service;

import java.io.UnsupportedEncodingException;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import spring.co.kr.dao.User;
import spring.co.kr.service.Interface.UserService;

public class UserServiceTx implements UserService {

	private UserService userService;
	private PlatformTransactionManager transactionManager;
	
	//transactionManager DI
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	//UserService DI
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void upgradeLevels() throws Exception {
		// TODO Auto-generated method stub
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			
			userService.upgradeLevels();
			this.transactionManager.commit(status);
			
		}catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}

	public void sendUpgradeEmail(User user) {
		// TODO Auto-generated method stub
		
	}

	public void add(User user) {
		// TODO Auto-generated method stub
		
	}

	public boolean canUpgradeLevel(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public void upgradeLevel(User user) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}

	public void upgradeLevelsInternal() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}

}
