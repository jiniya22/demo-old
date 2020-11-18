package me.jiniworld.demo.exception;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = -2832108568693227235L;

	public InvalidTokenException() {
        super("nonce값이 유효하지 않습니다.");
    }
}