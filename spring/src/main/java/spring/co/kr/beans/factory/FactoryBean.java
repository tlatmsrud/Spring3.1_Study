package spring.co.kr.beans.factory;

public interface FactoryBean<T> {
	T getObject() throws Exception;
	Class<? extends T> getObjectType();
	boolean isSingleton();
}
