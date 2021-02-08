package spring.co.kr.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor{
	
	PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager tansactionManager) {
		this.transactionManager = tansactionManager;
	}
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			Object ret = invocation.proceed();
			transactionManager.commit(status);
			System.out.println("advice");
			return ret;
		}catch(RuntimeException e) {
			transactionManager.rollback(status);
			throw e;
		}
	}
	
	

}
