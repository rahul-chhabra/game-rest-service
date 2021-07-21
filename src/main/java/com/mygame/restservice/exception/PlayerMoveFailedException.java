package com.mygame.restservice.exception;

/**
 * {@link PlayerMoveFailedException} is an unchecked exception which will be
 * thrown if move by player has failed for any reasons.
 * 
 * @author Rahul
 *
 */

public class PlayerMoveFailedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public PlayerMoveFailedException(String message) {
		super(message);
	}
}