package spring.co.kr.test;

import spring.co.kr.interf.Hello;

public class HelloTarget implements Hello{

	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "Hello "+name;
	}

	public String sayHI(String name) {
		// TODO Auto-generated method stub
		return "HI "+name;
	}

	public String sayThankYou(String name) {
		// TODO Auto-generated method stub
		return "Thank you "+name;
	}
	 
}
