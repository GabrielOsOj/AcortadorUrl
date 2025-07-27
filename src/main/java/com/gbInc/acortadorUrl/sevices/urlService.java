package com.gbInc.acortadorUrl.sevices;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.exception.UrlException;
import com.gbInc.acortadorUrl.exception.UrlExceptionConstants;
import com.gbInc.acortadorUrl.helper.UrlHelper;
import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import com.gbInc.acortadorUrl.persistence.repository.IurlRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class urlService implements IurlService {

	@Autowired
	UrlHelper urlHelper;

	@Autowired
	IurlRepository urlRepo;

	@Override
	public UrlDataDTO saveUrl(UrlIncoming url) {

		UrlDao newUrlDao = this.urlHelper.getUrlEntity(url);
		this.urlRepo.save(newUrlDao);

		UrlDataDTO urlDTO = this.urlHelper.toDTO(newUrlDao);
		urlDTO.setAccesCount(null);
		return urlDTO;
	}

	@Override
	public UrlDataDTO retrieveUrl(String urlShort) {

		UrlDao urlSaved = this.getUrlSavedFromShortUrl(urlShort);

		urlSaved.incrementAccessCount();
		this.urlRepo.save(urlSaved);
		urlSaved.setAccessCount(null);
		return this.urlHelper.toDTO(urlSaved);

	}

	@Override
	public UrlDataDTO updateUrl(String urlShort,UrlIncoming newUrl) {

		if(newUrl.getUrl().isBlank()){
			throw new UrlException(UrlExceptionConstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
		}
		
		UrlDao urlSaved = this.getUrlSavedFromShortUrl(urlShort);
		
		UrlDao urlUpdated = this.urlHelper.updateUrlData(urlSaved, newUrl.getUrl());
		
		this.urlRepo.save(urlUpdated);
		return this.urlHelper.toDTO(urlUpdated.setAccessCount(null));
	}

	@Override
	public void deleteUrl(String urlShort) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from
																		// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public void urlStats(String urlShort) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from
																		// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	private UrlDao getUrlSavedFromShortUrl(String urlShort) {

		return this.urlRepo.getSameIdentifier(urlShort)
			.orElseThrow(() -> new UrlException(UrlExceptionConstants.NOT_FOUND, HttpStatus.NOT_FOUND));

	}

}
