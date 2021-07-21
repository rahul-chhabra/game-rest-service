package com.mygame.restservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mygame.restservice.controller.GameController;

/**
 * A simple sanity check test that will fail if the application context cannot start.
 *
 *	@author Rahul
 */
@SpringBootTest
class GameRestServiceApplicationTests {
	
	@Autowired
	private GameController gameController;

	@Test
	void contextLoads() {
		assertThat(gameController).isNotNull();
	}

}
