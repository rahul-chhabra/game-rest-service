package com.mygame.restservice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.mygame.restservice.domain.Board;
import com.mygame.restservice.domain.GameState;
import com.mygame.restservice.domain.GameStatus;
import com.mygame.restservice.domain.Player;
import com.mygame.restservice.domain.PlayerState;
import com.mygame.restservice.exception.ColumnOutOfRangeException;
import com.mygame.restservice.exception.GameAlreadyRunningException;
import com.mygame.restservice.exception.GameStartPlayersNotConnectedException;
import com.mygame.restservice.exception.PlayerMoveFailedException;
import com.mygame.restservice.exception.PlayerNotFoundException;

/**
 * {@link GameServiceImpl} has the main business logic to validate the players, and maintain the game lifecycle.
 * 
 * @author Rahul
 *
 */
@Service(value = "GameServiceImpl")
public class GameServiceImpl implements GameService {
	
	private final Map<Integer, Player> players = new HashMap<Integer, Player>();
	private Board board;
	private GameStatus gameStatus;	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);	
	
	public GameServiceImpl() {
		initPlayers();
		board = new Board();
		gameStatus = new GameStatus(GameState.NOT_STARTED, board.getPrettyBoardView(), getRandomPlayerId());
	}
	
	private int getRandomPlayerId() {
		Random random = new Random();
		return random.nextInt(3-1) + 1;
	}
	
	private void initPlayers() {
		players.put(1, new Player(1));
		players.put(2, new Player(2));
	}

	@Override
	public Player getPlayerById(Integer playerId) {
		Player player = players.get(playerId);
		
		if(player == null)
			throw new PlayerNotFoundException(playerId);
		
		if (LOGGER.isDebugEnabled())
			LOGGER.debug(String.format("Updated Player: %s", player));
		
		return player;
	}

	@Override
	public Player updatePlayerById(Integer playerId, PlayerState playerState) {
		Player player = players.get(playerId);
		
		if(player == null)
			throw new PlayerNotFoundException(playerId);
		
		if(player !=null && playerState != null)
		{
			player.setPlayerState(playerState);
			checkIfGameAborted(playerId, playerState);
		}
		
		if (LOGGER.isDebugEnabled())
			LOGGER.debug(String.format("Updated Player: %s", player));
		
		return player;
	}
	
	private void checkIfGameAborted(Integer playerId, PlayerState playerState) {
		if(playerState == PlayerState.DISCONNECT && gameStatus.getGameState() == GameState.IN_PROGRESS)
		{
			LOGGER.info(String.format("Player %s has disconnetd. Aborting the game.", playerId));
			gameStatus.setGameState(GameState.ABORTED);
		}		
	}

	@Override
	public GameStatus gameStart() {
		String messageForClient = "";
		if(!areBothPlayersConnected())
		{
			throw new GameStartPlayersNotConnectedException("Both Players not connected yet");
		}
		else if(canGameBeStarted()) {
			board = new Board();			
			gameStatus = new GameStatus(GameState.IN_PROGRESS, board.getPrettyBoardView(), getRandomPlayerId());
			messageForClient = "Game Started";
		}
		else
			throw new GameAlreadyRunningException("Game already in progress");
		
		gameStatus.setMessageForClient(messageForClient);
		gameStatus.setBoardView(board.getPrettyBoardView());
		
		LOGGER.info("Game has been started successfully.");
		return gameStatus;
	}
	
	private boolean areBothPlayersConnected() {		
		return players.get(1).getPlayerState() == PlayerState.CONNECT
				&& players.get(2).getPlayerState() == PlayerState.CONNECT;		
	}
	
	private boolean canGameBeStarted() {
		// if Game not already in progress, then can be started
		if(gameStatus.getGameState()!=GameState.IN_PROGRESS)
			return true;
		else
			return false;
	}

	@Override
	public GameStatus playerMove(Integer playerId, Integer column) {
		String messageForClient = "";
		
		String MOVE_FAILED_MESSAGE = "Move failed for player id %d, and column %d";
		
		boolean isMoveSuccesful = false;
		
		switch(gameStatus.getGameState()) {
			case IN_PROGRESS:
				if(gameStatus.getPlayerWhoseNextMove() == playerId) {
					
					isValidColumn(column);
					
					if(board.moveByPlayer(playerId.intValue(), column))
					{
						isMoveSuccesful = true;
						updateGameState();
						switchPlayer();
						messageForClient = "Move completed succesfully";
					}
					else
						messageForClient = "Move failed.";					
				} else
						messageForClient = "Move failed. Other player turn";
				break;
			default:
				messageForClient="Move failed. Game is in state- " + gameStatus.getGameState();
		}
		
		if(!isMoveSuccesful)
		{
			LOGGER.error(String.format(MOVE_FAILED_MESSAGE, playerId, column));
			throw new PlayerMoveFailedException(messageForClient);
		}			
		
		gameStatus.setMessageForClient(messageForClient);
		gameStatus.setBoardView(board.getPrettyBoardView());
		gameStatus.setPlayerWinner(board.getWinnerId());
		
		if (LOGGER.isDebugEnabled())
			LOGGER.debug( String.format("Game Status - %s", gameStatus));
		
		return gameStatus;
	}
	
	private void updateGameState() {
		if(board.isGameOver())
		{
			gameStatus.setGameState(GameState.FINISHED);
		}
	}
	
	private void switchPlayer() {
		if(gameStatus.getPlayerWhoseNextMove()==1)
			gameStatus.setPlayerWhoseNextMove(2);
		else
			gameStatus.setPlayerWhoseNextMove(1);
	}
	
	private boolean isValidColumn(Integer column) throws ColumnOutOfRangeException{
		if(column>=1 && column <=9)
			return true;
		throw new ColumnOutOfRangeException("Column: " + column + " is not in valid range [1-9].");		
	}

	@Override
	public GameStatus getGameStatus() {
		board.checkBoardStatus();
		gameStatus.setPlayerWinner(board.getWinnerId());
		
		String messageForClient = "";
		
		switch(gameStatus.getGameState()) {
		
		case NOT_STARTED: messageForClient = "Game has not been started yet.";
						  break;
						  
		case IN_PROGRESS: messageForClient = "Waiting for move by Player " + gameStatus.getPlayerWhoseNextMove();
						  break;
			
		case FINISHED:    messageForClient = gameStatus.getPlayerWinner() == 0 ? "Game has been Drawn." : "Game Winner: " + gameStatus.getPlayerWinner() + ".Congratulations";
						  break;
						  
		case ABORTED: 	  messageForClient = "Game has been disconnected and aborted.";
						  break;
		}
		
		gameStatus.setMessageForClient(messageForClient);
		gameStatus.setBoardView(board.getPrettyBoardView());
		
		if (LOGGER.isDebugEnabled())
				LOGGER.debug( String.format("Game Status - %s", gameStatus));
		
		return gameStatus;
	}

}
