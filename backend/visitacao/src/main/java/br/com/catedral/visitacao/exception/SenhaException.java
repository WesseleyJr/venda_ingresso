package br.com.catedral.visitacao.exception;

public class SenhaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SenhaException(String message) {
		super(message);
	}
}
