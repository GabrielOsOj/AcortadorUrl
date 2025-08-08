package com.gbInc.acortadorUrl.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UrlErrorDTO {
		
	private Integer code; 
	private String msg;
	
}
