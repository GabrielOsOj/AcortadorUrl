package com.gbInc.acortadorUrl.controller;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.sevices.IurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urlShortener")
public class urlController {

	@Autowired
	private IurlService urlSv;
	
	@PostMapping("/shorten")
	public ResponseEntity<UrlDataDTO> shorten(@RequestBody UrlIncoming url){
		
		UrlDataDTO urlData = this.urlSv.saveUrl(url);
		
		return new ResponseEntity<UrlDataDTO>(urlData, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/{urlShort}")
	public ResponseEntity<UrlDataDTO> retrieveUrl(
	@PathVariable String urlShort){
		
		UrlDataDTO urlData = this.urlSv.retrieveUrl(urlShort);
		
		return new ResponseEntity<>(urlData,HttpStatus.OK);
	}
	
	@PutMapping("/{urlShort}")
	public ResponseEntity<UrlDataDTO> editUrl(@PathVariable String urlShort,
			@RequestBody UrlIncoming newUrl){
		
		UrlDataDTO urlEdited = this.urlSv.updateUrl(urlShort,newUrl);
		return new ResponseEntity<>(urlEdited,HttpStatus.OK);
		
	}
			
}
