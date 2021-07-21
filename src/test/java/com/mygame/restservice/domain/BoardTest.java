package com.mygame.restservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Rahul
 *
 */
class BoardTest {
	
	private Board board;

	private static final int PLAYER_1=1;
	private static final int PLAYER_2=2;	
	private static final int EXPECTED_WINNER_PLAYER_1=1;
	private static final int EXPECTED_WINNER_PLAYER_2=2;
	
	private static final String BOARD_MSG="Board:\n"; 
	
	@BeforeEach
	public void testSetup() {
		board = new Board();
	}
	
	@Test
	public void test_board_vertical_winner() {
		
		for(int i=0;i<5;i++)
		{
			board.moveByPlayer(PLAYER_2,9);
			
			if(i!=4)
				board.moveByPlayer(PLAYER_1,3);
		}
		
		assertEquals(EXPECTED_WINNER_PLAYER_2, board.getWinnerId(), BOARD_MSG + board.getPrettyBoardView());
	}
	
	@Test
	public void test_board_horizontal_winner() {
		
		for(int i=0;i<5;i++) {
			board.moveByPlayer(PLAYER_2,5+i);
			
			if(i!=4)
				board.moveByPlayer(PLAYER_1,1);
				
		}
		
		assertEquals(EXPECTED_WINNER_PLAYER_2, board.getWinnerId(), BOARD_MSG + board.getPrettyBoardView());
	}
	
	@Test
	public void test_board_main_diagonal_winner() {		
		
		board.moveByPlayer(PLAYER_1,5); 		board.moveByPlayer(1,5); 	board.moveByPlayer(1,5); 		board.moveByPlayer(1,5);	board.moveByPlayer(PLAYER_2,5);
		board.moveByPlayer(PLAYER_1,6); 		board.moveByPlayer(1,6); 	board.moveByPlayer(1,6); 		board.moveByPlayer(PLAYER_2,6);
		board.moveByPlayer(PLAYER_1,7); 		board.moveByPlayer(1,7);	board.moveByPlayer(PLAYER_2,7);
		board.moveByPlayer(PLAYER_1,8); 		board.moveByPlayer(PLAYER_2,8);
		board.moveByPlayer(PLAYER_2,9);	
		
	
		assertEquals(EXPECTED_WINNER_PLAYER_2, board.getWinnerId(), BOARD_MSG + board.getPrettyBoardView());
	}
	
	@Test	
	public void test_board_second_diagonal_winner() {		
		
		board.moveByPlayer(PLAYER_1,5);		
		board.moveByPlayer(PLAYER_2,6); 		board.moveByPlayer(PLAYER_1,6);
		board.moveByPlayer(PLAYER_2,7); 		board.moveByPlayer(PLAYER_2,7);		board.moveByPlayer(PLAYER_1,7);
		board.moveByPlayer(PLAYER_2,8); 		board.moveByPlayer(PLAYER_2,8); 	board.moveByPlayer(PLAYER_2,8); 		board.moveByPlayer(PLAYER_1,8);
		board.moveByPlayer(PLAYER_2,9); 		board.moveByPlayer(PLAYER_2,9); 	board.moveByPlayer(PLAYER_2,9); 		board.moveByPlayer(PLAYER_2,9);	board.moveByPlayer(PLAYER_1,9);
	
		assertEquals(EXPECTED_WINNER_PLAYER_1, board.getWinnerId(), BOARD_MSG + board.getPrettyBoardView());
	}
	

}
