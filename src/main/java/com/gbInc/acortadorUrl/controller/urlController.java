package com.gbInc.acortadorUrl.controller;

import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.sevices.IurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urlShortener")
public class urlController {

	@Autowired
	private IurlService urlSv;
	
	@PostMapping("/shorten")
	public ResponseEntity<String> shorten(@RequestBody UrlIncoming url){
		
		this.urlSv.saveUrl(url);
		
		return new ResponseEntity<String>("holanda", HttpStatus.ACCEPTED);
	}
	
}
