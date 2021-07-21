package com.mygame.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* {@link GameRestServiceApplication} is main {@link org.springframework.boot.autoconfigure.SpringBootApplication}.* 
* It finds the controller.
* 
* @author Rahul
*
*/
@SpringBootApplication
public class GameRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameRestServiceApplication.class, args);
	}

}
