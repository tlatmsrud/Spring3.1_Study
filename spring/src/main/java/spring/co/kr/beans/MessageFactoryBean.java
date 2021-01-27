package spring.co.kr.beans;

import org.springframework.beans.factory.FactoryBean;

import spring.co.kr.factorybean.Message;

public class MessageFactoryBean implements FactoryBean<Message>{

	String text;
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Message getObject() throws Exception {
		// TODO Auto-generated method stub
		return Message.newMessage(text);
	}

	public Class<? extends Message> getObjectType() {
		// TODO Auto-generated method stub
		return Message.class;
	}

	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
