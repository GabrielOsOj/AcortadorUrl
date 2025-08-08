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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://127.0.0.1:5500", "http://localhost:5500" })
@RequestMapping("/urlShortener")
public class urlController {

	@Autowired
	private IurlService urlSv;

	@Autowired
	private Bucket bucket;

	private final UrlException urlException;

	public urlController() {
		this.urlException = new UrlException(UrlExceptionConstants.TOO_MANY_REQUESTS, HttpStatus.TOO_MANY_REQUESTS);
	}

	@PostMapping("/shorten")
	public ResponseEntity<UrlDataDTO> shorten(@RequestBody UrlIncoming url) {

		this.checkBucket();

		UrlDataDTO urlData = this.urlSv.saveUrl(url);
		return new ResponseEntity<>(urlData, HttpStatus.ACCEPTED);

	}

	@GetMapping("/{urlShort}")
	public ResponseEntity<String> retrieveUrl(@PathVariable String urlShort) {

		this.checkBucket();

		UrlDataDTO urlData = this.urlSv.retrieveUrl(urlShort);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", urlData.getUrl());

		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}

	@PutMapping("/{urlShort}")
	public ResponseEntity<UrlDataDTO> editUrl(@PathVariable String urlShort, @RequestBody UrlIncoming newUrl) {

		this.checkBucket();

		UrlDataDTO urlEdited = this.urlSv.updateUrl(urlShort, newUrl);
		return new ResponseEntity<>(urlEdited, HttpStatus.OK);

	}

	@DeleteMapping("/{userId}/{urlShort}")
	public ResponseEntity<Void> deleteUrl(@PathVariable String userId, @PathVariable String urlShort) {

		this.checkBucket();

		this.urlSv.deleteUrl(userId, urlShort);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{urlShort}/stats")
	public ResponseEntity<UrlDataDTO> getStats(@PathVariable String urlShort) {

		this.checkBucket();

		UrlDataDTO urlSaved = this.urlSv.urlStats(urlShort);

		return new ResponseEntity<>(urlSaved, HttpStatus.OK);

	}

	@PostMapping("/getAll")
	public ResponseEntity<List<UrlDataDTO>> getAllUrlByOwner(@RequestBody UrlIncoming urlIncoming) {

		this.checkBucket();

		List<UrlDataDTO> allUrlsByOwner = this.urlSv.getAllUrlsByOwner(urlIncoming);

		return new ResponseEntity<>(allUrlsByOwner, HttpStatus.OK);
	}

	private void checkBucket() {
		if (this.bucket.tryConsume(1)) {
			return;
		}
		throw this.urlException;
	}

}
