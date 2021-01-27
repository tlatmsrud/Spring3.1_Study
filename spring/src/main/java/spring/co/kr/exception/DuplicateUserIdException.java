package spring.co.kr.exception;

public class DuplicateUserIdException extends RuntimeException{
	public DuplicateUserIdException(Throwable cause) {
		super(cause);
	}

}
