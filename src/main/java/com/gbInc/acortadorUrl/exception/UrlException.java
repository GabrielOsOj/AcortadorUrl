package com.gbInc.acortadorUrl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UrlException extends RuntimeException{

	HttpStatus httpCode;
	
	public UrlException(String msg,HttpStatus code){
		super(msg);
		this.httpCode = code;
	}
	
}
