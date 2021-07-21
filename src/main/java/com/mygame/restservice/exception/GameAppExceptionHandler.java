package com.mygame.restservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * {@link GameAppExceptionHandler} is of {@link ExceptionHandler} to handle
 * different exception in application. 
 * 
 * @author Rahul
 *
 */

@ControllerAdvice
public class GameAppExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameAppExceptionHandler.class);

	@ExceptionHandler({ ColumnOutOfRangeException.class })
	public ResponseEntity<Object>  invalidOptionException(final ColumnOutOfRangeException e) {
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ GameAlreadyRunningException.class })
	public ResponseEntity<Object>  gameAlreadyRunningException(final GameAlreadyRunningException e) {
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ PlayerMoveFailedException.class })
	public ResponseEntity<Object>  columnAlreadyFullException(final PlayerMoveFailedException e) {
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ PlayerNotFoundException.class })
	public ResponseEntity<Object>  playerNotFoundException(final PlayerNotFoundException e) {
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
