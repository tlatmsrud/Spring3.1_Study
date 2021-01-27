package spring.co.kr.proxy;

import spring.co.kr.interf.Hello;

public class HelloUppercase implements Hello{

	private Hello hello;
	
	public HelloUppercase(Hello hello) {
		this.hello = hello;
	}
	
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return hello.sayHello(name).toUpperCase();
	}

	public String sayHI(String name) {
		// TODO Auto-generated method stub
		return hello.sayHI(name).toUpperCase();
	}

	public String sayThankYou(String name) {
		// TODO Auto-generated method stub
		return hello.sayThankYou(name).toUpperCase();
	}
	
}
