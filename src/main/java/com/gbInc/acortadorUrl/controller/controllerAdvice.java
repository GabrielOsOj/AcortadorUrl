package com.gbInc.acortadorUrl.controller;

import com.gbInc.acortadorUrl.DTO.UrlErrorDTO;
import com.gbInc.acortadorUrl.exception.UrlException;
import com.gbInc.acortadorUrl.exception.UrlExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class controllerAdvice {
	
	@ExceptionHandler(value = UrlException.class)
	public ResponseEntity<UrlErrorDTO> errorHandler(UrlException ex){
		return new ResponseEntity<UrlErrorDTO>(
				new UrlErrorDTO(ex.getHttpCode().value(),ex.getMessage())
		,ex.getHttpCode());
	}
	
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<UrlErrorDTO>errorDataTipeHandler(){
		return new ResponseEntity<UrlErrorDTO>(
				new UrlErrorDTO(HttpStatus.BAD_REQUEST.value(),UrlExceptionConstants.BAD_REQUEST)
		,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NoResourceFoundException.class)
	public ResponseEntity<UrlErrorDTO>errorNotFoundResourceException(){
		return new ResponseEntity<UrlErrorDTO>(
				new UrlErrorDTO(HttpStatus.NOT_FOUND.value(),UrlExceptionConstants.BAD_RESOURCE)
		,HttpStatus.NOT_FOUND);
	}
}
