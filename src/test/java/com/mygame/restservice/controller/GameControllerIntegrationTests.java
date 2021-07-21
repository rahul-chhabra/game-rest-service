package com.mygame.restservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygame.restservice.domain.GameStatus;
import com.mygame.restservice.domain.Player;
import com.mygame.restservice.domain.PlayerState;

/**
 * @author Rahul
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GameControllerIntegrationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameControllerIntegrationTests.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
//	@Mock
//	private RestTemplate
	
	@Test
	@Order(1)
	@DisplayName("Valid Player 1")
	public void testGetValidPlayer1() throws Exception {
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/player/1")
				.accept(MediaType.APPLICATION_JSON);
		
		final MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		final String expectedPlayer1 = "{\"playerId\":1,\"playerState\":\"DISCONNECT\"}";
		
		LOGGER.info("----------------------------------------------------------------------------------------------------------");
		
		LOGGER.info("testGetValidPlayer1"+result.getResponse().getContentAsString());
		LOGGER.info("----------------------------------------------------------------------------------------------------------");

		System.out.println(result);
		JSONAssert.assertEquals(expectedPlayer1, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@Order(2)
	@DisplayName("Connect Player 1")
	public void testPutConnectPlayer1() throws Exception {
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/player/1")
				.content("\"CONNECT\"")
				.contentType(MediaType.APPLICATION_JSON);
		
		final MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		final String expectedPlayer1 = "{\"playerId\":1,\"playerState\":\"CONNECT\"}";
		
		LOGGER.info("----------------------------------------------------------------------------------------------------------");
		
		LOGGER.info("testPutConnectPlayer1"+result.getResponse().getContentAsString());
		LOGGER.info("----------------------------------------------------------------------------------------------------------");

		JSONAssert.assertEquals(expectedPlayer1, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@Order(3)
	@DisplayName("Connect Player 2")
	public void testPutConnectPlayer2() throws Exception {
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/player/2")
				.content("\"CONNECT\"")
				.contentType(MediaType.APPLICATION_JSON);
		
		final MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		final String expectedPlayer2 = "{\"playerId\":2,\"playerState\":\"CONNECT\"}";
		
		LOGGER.info("----------------------------------------------------------------------------------------------------------");
		
		LOGGER.info("testPutConnectPlayer2"+result.getResponse().getContentAsString());
		LOGGER.info("----------------------------------------------------------------------------------------------------------");

		System.out.println(result);
		JSONAssert.assertEquals(expectedPlayer2, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@Order(4)
	@DisplayName("Game Start")
	public void testPostGameStart() throws Exception {
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/game/start");
		
		final MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		final String expectedGameStart = "{\"gameState\":\"IN_PROGRESS\"}";
		
		LOGGER.info("----------------------------------------------------------------------------------------------------------");
		
		LOGGER.info("testPostGameStart"+result.getResponse().getContentAsString());
		LOGGER.info("----------------------------------------------------------------------------------------------------------");

		System.out.println(result);
		JSONAssert.assertEquals(expectedGameStart, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@Order(5)
	@DisplayName("Get Game Status")
	public void testGetGameStatus() throws Exception {
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/game/status");
		
		final MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		final String expectedGameStart = "{\"gameState\":\"IN_PROGRESS\"}";
		
		GameStatus gameStatus = objectMapper.readValue(result.getResponse().getContentAsString(), GameStatus.class);
		
		LOGGER.info("----------------------------------------------------------------------------------------------------------");
		
		LOGGER.info("testPostGameStart: "+result.getResponse().getContentAsString());
		LOGGER.info("gameStatus: "+ gameStatus.getPlayerWhoseNextMove());
		LOGGER.info("----------------------------------------------------------------------------------------------------------");

		System.out.println(result);
		JSONAssert.assertEquals(expectedGameStart, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@Order(6)
	@DisplayName("Test Post Player Move")
	public void testPostPlayerMove() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/game/status");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		GameStatus gameStatus = objectMapper.readValue(result.getResponse().getContentAsString(), GameStatus.class);
		int playerWhoseNextMove = gameStatus.getPlayerWhoseNextMove();
		
		int expectedPlayerWhoseNextMoveAfterThis = playerWhoseNextMove == 1 ? 2 : 1;
		
		requestBuilder = MockMvcRequestBuilders.post("/game/player/"+playerWhoseNextMove+"/move")
							.content("1")
							.contentType(MediaType.APPLICATION_JSON);
		
		result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		gameStatus = objectMapper.readValue(result.getResponse().getContentAsString(), GameStatus.class);
		int actualPlayerWhoseNextMove = gameStatus.getPlayerWhoseNextMove();
		
		String expectedMessage = "{\"playerWhoseNextMove\":" + actualPlayerWhoseNextMove + "}";
		
		LOGGER.info("----------------------------------------------------------------------------------------------------------");
		
		LOGGER.info("testPostPlayerMove: "+result.getResponse().getContentAsString());
		LOGGER.info("gameStatus: "+ gameStatus.getPlayerWhoseNextMove());
		LOGGER.info("----------------------------------------------------------------------------------------------------------");


		JSONAssert.assertEquals(expectedMessage, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	@Order(7)
	@DisplayName("Incorrect column")
	public void testPostPlayerMoveIncorrectColumn() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/game/status");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		GameStatus gameStatus = objectMapper.readValue(result.getResponse().getContentAsString(), GameStatus.class);
		int playerWhoseNextMove = gameStatus.getPlayerWhoseNextMove();
		
		requestBuilder = MockMvcRequestBuilders.post("/game/player/"+playerWhoseNextMove+"/move")
							.content("10")
							.contentType(MediaType.APPLICATION_JSON);
		
		 mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	@Order(8)
	@DisplayName("Test Abort Game")
	public void testGameAborted() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/player/2")
				.content("\"DISCONNECT\"")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		requestBuilder = MockMvcRequestBuilders.get("/game/status");
		
		result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		final String expectedGameStatus = "{\"gameState\":\"ABORTED\"}";
		
		JSONAssert.assertEquals(expectedGameStatus, result.getResponse().getContentAsString(), false);		
	}
	
	@Test
	@Order(9)
	@DisplayName("Test Game Winner")
	public void testGameWinner() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/player/2")
				.content("\"CONNECT\"")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		requestBuilder = MockMvcRequestBuilders.post("/game/start");
		
		result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		final String expectedGameStatus = "{\"gameState\":\"IN_PROGRESS\"}";
		
		JSONAssert.assertEquals(expectedGameStatus, result.getResponse().getContentAsString(), false);
		
		GameStatus gameStatus = objectMapper.readValue(result.getResponse().getContentAsString(), GameStatus.class);
		
		int firstChancePlayer = gameStatus.getPlayerWhoseNextMove();
		int otherPlayer = firstChancePlayer == 1 ? 2 : 1;
		
		for(int i=0;i<5;i++) {
			requestBuilder = MockMvcRequestBuilders.post("/game/player/"+firstChancePlayer+"/move")
					.content("1")
					.contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
			
			if(i!=4) {
				requestBuilder = MockMvcRequestBuilders.post("/game/player/"+otherPlayer+"/move")
						.content("2")
						.contentType(MediaType.APPLICATION_JSON);
				
				mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
			}
			
		}
		
		requestBuilder = MockMvcRequestBuilders.get("/game/status");
		
		result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		final String expectedGameWinner = "{\"gameState\":\"FINISHED\", \"playerWinner\":"+ firstChancePlayer + "}";
		
		JSONAssert.assertEquals(expectedGameWinner, result.getResponse().getContentAsString(), false);		
	}
	

}
