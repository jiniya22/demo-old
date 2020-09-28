package me.jiniworld.demo.exception;

public class AuthorizationHeaderNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 4858506469476160448L;

	public AuthorizationHeaderNotExistsException() {
        super("Authorization 헤더가 없습니다.");
    }
}
