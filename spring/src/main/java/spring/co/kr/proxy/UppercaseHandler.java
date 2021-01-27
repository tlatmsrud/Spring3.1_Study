package spring.co.kr.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import spring.co.kr.interf.Hello;

public class UppercaseHandler implements InvocationHandler{

	Object target;
	
	public UppercaseHandler(Object target) {
		this.target=target;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Object ret = method.invoke(target, args); //target 오프젝트를 DI 해주고, target 오브젝트 타입으로 전달받은 메소드를 실행시킨다.
		
		if(ret instanceof String && method.getName().startsWith("say")) {
			return ((String)ret).toUpperCase(); //실행결과에 대해 upperCase 한다.
		}else {
			return ret;
		}
		
	}
	
	
	
	
}
