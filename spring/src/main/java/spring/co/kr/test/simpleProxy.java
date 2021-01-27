package spring.co.kr.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Proxy;

import org.junit.Test;

import spring.co.kr.interf.Hello;
import spring.co.kr.proxy.HelloUppercase;
import spring.co.kr.proxy.UppercaseHandler;

public class simpleProxy {

	/*
	 * @Test public void simpleProxy() { Hello hello = new HelloTarget();
	 * assertThat(hello.sayHello("Toby"), is("Hello Toby"));
	 * assertThat(hello.sayHI("Toby"), is("HI Toby"));
	 * assertThat(hello.sayThankYou("Toby"), is("Thank you Toby"));
	 * 
	 * 
	 * Hello proxiedHello = new HelloUppercase(new HelloTarget());
	 * assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
	 * assertThat(proxiedHello.sayHI("Toby"), is("HI TOBY"));
	 * assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
	 * 
	 * Hello proxiedHello =
	 * (Hello)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]
	 * {Hello.class}, new UppercaseHandler(new HelloTarget()));
	 * 
	 * assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
	 * assertThat(proxiedHello.sayHI("Toby"), is("HI TOBY"));
	 * assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
	 * 
	 * }
	 */

	@Test
	public void dynamicProxy() {

		String name = "ssk";
		Hello proxiedHello = (Hello) Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[] { Hello.class },
				new UppercaseHandler(new HelloTarget())
			);

		assertThat(proxiedHello.sayHello(name), is("HELLO SSK"));
	}
}
