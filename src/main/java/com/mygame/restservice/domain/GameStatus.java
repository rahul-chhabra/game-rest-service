package com.mygame.restservice.domain;

/**
 * {@link GameStatus} represents the full status of the game.
 *
 * @author Rahul
 *
 */
public final class GameStatus {

	private GameState gameState;
	private String boardView;
	private Integer playerWhoseNextMove;
	private Integer playerWinner;
	private String messageForClient;
	
	public GameStatus(GameState gameState,String boardView, Integer playerWhoseNextMove ) {
		this.gameState = gameState;
		this.boardView = boardView;
		this.playerWhoseNextMove = playerWhoseNextMove;
		playerWinner = 0;
	}	
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public String getBoardView() {
		return boardView;
	}
	
	public void setBoardView(String boardView) {
		this.boardView = boardView;
	}
	
	public Integer getPlayerWhoseNextMove() {
		return playerWhoseNextMove;
	}
	
	public void setPlayerWhoseNextMove(Integer playerWhoseNextMove) {
		this.playerWhoseNextMove = playerWhoseNextMove;
	}
	
	public Integer getPlayerWinner() {
		return playerWinner;
	}
	
	public void setPlayerWinner(Integer playerWinner) {
		this.playerWinner = playerWinner;
	}

	public String getMessageForClient() {
		return messageForClient;
	}

	public void setMessageForClient(String messageForClient) {
		this.messageForClient = messageForClient;
	}

	@Override
	public String toString() {
		return "GameStatus [gameState=" + gameState + ", boardView=" + boardView + ", playerWhoseNextMove="
				+ playerWhoseNextMove + ", playerWinner=" + playerWinner + ", messageForClient=" + messageForClient
				+ "]";
	}		
}
