package com.mygame.restservice.exception;

/**
 * {@link GameAlreadyRunningException} is an unchecked exception which will be
 * thrown if game is already running, and client tries to start it again.
 * 
 * @author Rahul
 *
 */
public class GameAlreadyRunningException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public GameAlreadyRunningException(String message) {
		super(message);
	}	
}
