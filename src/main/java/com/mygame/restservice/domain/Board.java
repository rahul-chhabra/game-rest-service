package com.mygame.restservice.domain;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Board} has the main business logic for the actual board game.
 * 
 * @author Rahul
 *
 */
public final class Board {
	
	private final int ROWS =6;	
	private final int COLUMNS = 9;
	private final int MAXIMUM_MOVES = 6 * COLUMNS; 
	private final int CONSECUTIVE = 5;
	
	private int[][] board;
	private boolean isGameOver;
	private int winnerPlayerId;	
	private int totalMovesDone;	 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);	
	
	public Board() {
		super();		
		newBoard();
	}

	public void newBoard() {
		board= new int[ROWS][COLUMNS];
		isGameOver = false;
		winnerPlayerId = 0;
		totalMovesDone = 0;		
	}
	
	public boolean moveByPlayer(int playerId,int column) {
		
		// game already over
		if(isGameOver)
			return false;
		
		// Invalid column
		if(column<1 || column > 9)
			return false;
		else 		
			// Updating column value
			column = column -1;
		
		for(int i=ROWS-1;i>=0;i--)
		{
			if(board[i][column] == 0)
			{
				board[i][column] = playerId;
				totalMovesDone ++ ;
				checkBoardStatus();
				return true;
			}
		}
		
		// No space available in the column
		return false;			
	}
	
	public void checkBoardStatus() {
		boolean isWinnerFound = false;
	
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				try {
					int horizontal_result = checkHorizontal(i, j);
					if (horizontal_result != 0) {
						isWinnerFound = true;
						winnerPlayerId = horizontal_result;
						break;
					}

					int vertical_result = checkVertical(i, j);
					if (vertical_result != 0) {
						isWinnerFound = true;
						winnerPlayerId = vertical_result;
						break;
					}

					int main_diagonal_result = checkMainDiagonal(i, j);
					if (main_diagonal_result != 0) {
						isWinnerFound = true;
						winnerPlayerId = main_diagonal_result;
						break;
					}

					int secondary_diagonal_result = checkSecondaryDiagonal(i, j);
					if (secondary_diagonal_result != 0) {
						isWinnerFound = true;
						winnerPlayerId = secondary_diagonal_result;
						break;
					}
				} catch (Exception e) {
					LOGGER.error("i:"+ i +",j: " + j);
					throw e;
				}
			}
		}
		
		
		if(isWinnerFound || totalMovesDone >= MAXIMUM_MOVES )
			isGameOver = true;
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
	public int getWinnerId() {
		return winnerPlayerId;
	}
	
	private int checkHorizontal(int i, int j) {
		boolean isWinnerFound = true;
		int playerId=board[i][j];
		
		for(int k = 1; k< CONSECUTIVE; k ++)
		{
			if(j +k >= COLUMNS || playerId != board[i][j+k] )
			{
				playerId = 0;
				isWinnerFound = false;
				break;
			}			
		}
		return playerId;
	}
	
	private int checkVertical(int i, int j) {
		boolean isWinnerFound = true;
		int playerId=board[i][j];
		
		for(int k = 1; k< CONSECUTIVE; k ++)
		{
			if(i + k >= ROWS || playerId != board[i+k][j] )
			{
				playerId = 0;
				isWinnerFound = false;
				break;
			}			
		}
		return playerId;
	}
	
	private int checkMainDiagonal(int i, int j) {
		boolean isWinnerFound = true;
		int playerId=board[i][j];
		
		for(int k = 1; k< CONSECUTIVE; k ++)
		{
			// Do not overflow the board while going diagonal
			if( i + k >= ROWS || j + k >= COLUMNS || playerId != board[i+k][j+k] )
			{
				playerId = 0;
				isWinnerFound = false;
				break;
			}			
		}
		return playerId;
	}
	
	private int checkSecondaryDiagonal(int i, int j) {
		boolean isWinnerFound = true;
		int playerId=board[i][j];
		
		for(int k = 1; k< CONSECUTIVE; k ++)
		{
			// Do not overflow the board while going diagonal
			if( i - k < 0 || j + k >= COLUMNS || playerId != board[i-k][j+k] )
			{
				playerId = 0;
				isWinnerFound = false;
				break;
			}			
		}
		return playerId;
	}
	
	public String getPrettyBoardView() {		
		StringBuilder prettyBoard = new StringBuilder();
		String SQUARE_BRACKET_START = "[";
		String SQUARE_BRACKET_END = "]";
		String NEW_LINE = "\n";
		
		for(int i=0;i<ROWS;i++)
		{
				for(int j=0;j<COLUMNS;j++)
				{
					String display =" ";
					
					if(board[i][j]!=0)						
						display = String.valueOf(board[i][j]);
					
					prettyBoard = prettyBoard.append(SQUARE_BRACKET_START).append(display).append(SQUARE_BRACKET_END);		
				}
				prettyBoard.append(NEW_LINE);
			}		
		
		return prettyBoard.toString();
	}

	@Override
	public String toString() {
		return "Board [ROWS=" + ROWS + ", COLUMNS=" + COLUMNS + ", MAXIMUM_MOVES=" + MAXIMUM_MOVES + ", CONSECUTIVE="
				+ CONSECUTIVE + ", board=" + Arrays.toString(board) + ", isGameOver=" + isGameOver + ", winnerPlayerId="
				+ winnerPlayerId + ", totalMovesDone=" + totalMovesDone + "]";
	}
}
