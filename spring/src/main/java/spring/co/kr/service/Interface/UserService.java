package spring.co.kr.service.Interface;

import java.io.UnsupportedEncodingException;

import spring.co.kr.dao.User;

public interface UserService {
	
	public void upgradeLevels() throws Exception;
	public void sendUpgradeEmail(User user);
	public void add(User user);
	public boolean canUpgradeLevel(User user);
	public void upgradeLevel(User user) throws UnsupportedEncodingException;
	public void upgradeLevelsInternal() throws UnsupportedEncodingException;
	
}
