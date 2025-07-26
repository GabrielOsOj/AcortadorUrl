package com.gbInc.acortadorUrl.controller;

import com.gbInc.acortadorUrl.DTO.UrlErrorDTO;
import com.gbInc.acortadorUrl.exception.UrlException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class controllerAdvice {
	
	@ExceptionHandler(value = UrlException.class)
	public ResponseEntity<UrlErrorDTO> errorHandler(UrlException ex){
		return new ResponseEntity<UrlErrorDTO>(
				new UrlErrorDTO(ex.getHttpCode().value(),ex.getMessage())
		,ex.getHttpCode());
	}
	
}
