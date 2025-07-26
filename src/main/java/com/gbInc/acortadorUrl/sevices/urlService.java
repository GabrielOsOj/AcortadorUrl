package com.gbInc.acortadorUrl.sevices;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.helper.UrlHelper;
import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import com.gbInc.acortadorUrl.persistence.repository.IurlRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void retrieveUrl(String urlShort) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from
																		// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public void updateUrl(String urlShort) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from
																		// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

}
