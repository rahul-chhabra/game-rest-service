package com.mygame.restservice.exception;

/**
 * {@link ColumnOutOfRangeException} is an unchecked exception which will be
 * thrown if column does not exist in the valid range.
 * 
 * @author Rahul
 *
 */
public class ColumnOutOfRangeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ColumnOutOfRangeException(Long column) {
		super(String.format("Column %d not in the valid range [1-9]", column));		
	}
	
	public ColumnOutOfRangeException(String message) {
		super(message);
	}
}