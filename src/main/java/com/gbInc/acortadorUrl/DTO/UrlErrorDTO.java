package com.gbInc.acortadorUrl.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public class UrlErrorDTO {
		
	private Integer code; 
	private String msg;
	
}
