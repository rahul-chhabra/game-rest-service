package com.mygame.restservice.domain;

/**
* {@link Player} is the main actor for game.
*
* @author Rahul
*
*/
public final class Player {
	
	private Integer playerId;
	private PlayerState playerState;
	
	public Player(Integer playerId) {
		super();
		this.playerId = playerId;
		this.playerState = PlayerState.DISCONNECT;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	
	public PlayerState getPlayerState() {
		return playerState;
	}
	
	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	@Override
	public String toString() {
		return "Player [playerId=" + playerId + ", playerState=" + playerState + "]";
	}	
}
