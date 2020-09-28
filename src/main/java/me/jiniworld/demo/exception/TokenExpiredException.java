package me.jiniworld.demo.exception;

public class TokenExpiredException extends RuntimeException {
	private static final long serialVersionUID = 2036611901536356933L;

	public TokenExpiredException() {
        super("토큰이 만료되었습니다.");
    }
}