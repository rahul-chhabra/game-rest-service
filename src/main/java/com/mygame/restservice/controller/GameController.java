package com.mygame.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mygame.restservice.domain.GameStatus;
import com.mygame.restservice.domain.Player;
import com.mygame.restservice.domain.PlayerState;
import com.mygame.restservice.service.GameService;

/**
 * {@link GameController} is a spring {@link RestController} controller to handle REST requests towards game endpoint.
 * 
 * @author Rahul
 *
 */
@RestController
public class GameController {

	@Autowired
	@Qualifier("GameServiceImpl")
	private GameService gameService;

	@GetMapping("/player/{playerId}")
	public Player getPlayerById(@PathVariable Integer playerId) {
		return gameService.getPlayerById(playerId);
	}

	@PutMapping("/player/{playerId}")
	public Player updatePlayerById(@PathVariable Integer playerId, @RequestBody PlayerState playerState) {
		return gameService.updatePlayerById(playerId, playerState);
	}

	@PostMapping("/game/start")
	public GameStatus gameStart() {
		return gameService.gameStart();
	}

	@PostMapping("/game/player/{playerId}/move")
	public GameStatus playerMove(@PathVariable Integer playerId, @RequestBody Integer column) {
		return gameService.playerMove(playerId, column);
	}

	@GetMapping("/game/status")
	public GameStatus getGameStatus() {
		return gameService.getGameStatus();
	}

}
