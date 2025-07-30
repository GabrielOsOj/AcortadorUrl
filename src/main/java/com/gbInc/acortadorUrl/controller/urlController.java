package com.gbInc.acortadorUrl.controller;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.exception.UrlException;
import com.gbInc.acortadorUrl.exception.UrlExceptionConstants;
import com.gbInc.acortadorUrl.sevices.IurlService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	private final Bucket bucket;

	private final UrlException urlException;

	public urlController() {

		Bandwidth limit = Bandwidth.builder().capacity(10).refillIntervally(10, Duration.ofMinutes(1)).build();

		this.bucket = Bucket.builder().addLimit(limit).build();

		this.urlException = new UrlException(UrlExceptionConstants.TOO_MANY_REQUESTS, HttpStatus.TOO_MANY_REQUESTS);
	}

	@PostMapping("/shorten")
	public ResponseEntity<UrlDataDTO> shorten(@RequestBody UrlIncoming url) {

		if (!this.bucket.tryConsume(1)) {
			throw this.urlException;
		}

		UrlDataDTO urlData = this.urlSv.saveUrl(url);
		return new ResponseEntity<>(urlData, HttpStatus.ACCEPTED);

	}

	@GetMapping("/{urlShort}")
	public ResponseEntity<String> retrieveUrl(@PathVariable String urlShort) {

		if (!this.bucket.tryConsume(1)) {
			throw this.urlException;
		}

		UrlDataDTO urlData = this.urlSv.retrieveUrl(urlShort);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", urlData.getUrl());

		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}

	@PutMapping("/{urlShort}")
	public ResponseEntity<UrlDataDTO> editUrl(@PathVariable String urlShort, @RequestBody UrlIncoming newUrl) {

		if (!this.bucket.tryConsume(1)) {
			throw this.urlException;
		}

		UrlDataDTO urlEdited = this.urlSv.updateUrl(urlShort, newUrl);
		return new ResponseEntity<>(urlEdited, HttpStatus.OK);

	}

	@DeleteMapping("/{urlShort}")
	public ResponseEntity<Void> deleteUrl(@PathVariable String urlShort) {

		if (!this.bucket.tryConsume(1)) {
			throw this.urlException;
		}

		this.urlSv.deleteUrl(urlShort);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{urlShort}/stats")
	public ResponseEntity<UrlDataDTO> getStats(@PathVariable String urlShort) {

		if (!this.bucket.tryConsume(1)) {
			throw this.urlException;
		}

		UrlDataDTO urlSaved = this.urlSv.urlStats(urlShort);

		return new ResponseEntity<>(urlSaved, HttpStatus.OK);

	}

}
