package com.mygame.restservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mygame.restservice.domain.GameState;
import com.mygame.restservice.domain.GameStatus;
import com.mygame.restservice.domain.PlayerState;
import com.mygame.restservice.exception.GameAlreadyRunningException;

/**
 * @author Rahul
 *
 */
public class GameServiceTest {

	private GameService testObj;
	
	@BeforeEach
	public void testSetup() {
		testObj = new GameServiceImpl();
	}
	
	@Test
	@DisplayName("Get game status")
	public void testGetGameStatus() {
		GameStatus gameStatus = testObj.getGameStatus();
		
		assertEquals(GameState.NOT_STARTED, gameStatus.getGameState());
	}
	
	@Test
	@DisplayName("Check for Exception- GameAlreadyRunningException ")
	public void testGameStart() {
		// Given
		testObj.updatePlayerById(1, PlayerState.CONNECT);
		testObj.updatePlayerById(2, PlayerState.CONNECT);
		testObj.gameStart();
		
		// when and then 
		assertThrows(GameAlreadyRunningException.class, () -> {testObj.gameStart();});		
	}

}
