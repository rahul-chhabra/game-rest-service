package com.mygame.restservice.exception;

/**
 * {@link GameStartPlayersNotConnectedException} is an unchecked exception which will be
 * thrown if 2 players are not connected and the client tries to start the game.
 * 
 * @author Rahul
 *
 */
public class GameStartPlayersNotConnectedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public GameStartPlayersNotConnectedException(String message) {
		super(message);
	}	
}