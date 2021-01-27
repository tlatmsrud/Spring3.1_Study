package spring.co.kr.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

import spring.co.kr.interf.Hello;

public class DynamicProxyTest {


	@Test
	public void proxyFactoryBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());
		
		Hello proxiedHello = (Hello)pfBean.getObject();
		
		assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
	}
	
	static class UppercaseAdvice implements MethodInterceptor{
		public Object invoke(MethodInvocation invocation) throws Throwable{
			String ret = (String)invocation.proceed();
			return ret.toUpperCase();
		}
	}
}
