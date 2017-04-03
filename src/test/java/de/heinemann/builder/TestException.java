package de.heinemann.builder;

public class TestException {
	
	private String exception;
	private String message;
	
	public TestException(Class<? extends Exception> exceptionClass, String message) {
		this.exception = exceptionClass.getName();
		this.message = message;
	}
	
}
