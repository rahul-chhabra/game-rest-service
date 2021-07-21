package com.mygame.restservice.service;

import com.mygame.restservice.domain.GameStatus;
import com.mygame.restservice.domain.Player;
import com.mygame.restservice.domain.PlayerState;

/**
 * {@link GameService} is the interface for game service, used by {@link GameController}.
 * 
 * @author Rahul
 *
 */
public interface GameService {
	
	public Player getPlayerById(Integer playerId);
	
	public Player updatePlayerById(Integer playerId, PlayerState playerState);
	
	public GameStatus gameStart();
	
	public GameStatus playerMove(Integer playerId, Integer column);
	
	public GameStatus getGameStatus();

}
