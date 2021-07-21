package com.mygame.restservice.exception;

/**
 * {@link PlayerNotFoundException} is an unchecked exception which will be
 * thrown if player id is invalid.
 * 
 * @author Rahul
 *
 */

public class PlayerNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public PlayerNotFoundException(Integer playerId) {
		super(String.format("Player with Id %d not found", playerId));
	}
}