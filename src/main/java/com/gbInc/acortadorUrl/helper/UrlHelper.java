package com.gbInc.acortadorUrl.helper;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import com.gbInc.acortadorUrl.persistence.repository.IurlRepository;
import com.gbInc.acortadorUrl.util.IdGenerator;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UrlHelper {
	
	final private String DATE_FORMAT = "yyyy-mm-dd'T'HH:mm:ss'Z'";
	
	@Autowired
	IurlRepository urlRepo;	
	
	IdGenerator idGenerator;
	
	public UrlHelper(){
		this.idGenerator = new IdGenerator();
	}
	
	public UrlDao getUrlEntity(UrlIncoming urlLong){
		
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));
		
		UrlDao urlNew = UrlDao.builder()
				.id(null)
				.url(urlLong.getUrl())
				.shortCode(this.generateUniqueIdentifier())
				.createdAt(dateNow)
				.updatedAt(dateNow)
				.accesCount(0)
				.build();
		
		return urlNew; 
		
	}
	
	public UrlDataDTO toDTO(UrlDao urlDao){
		
		return UrlDataDTO.builder()
				.id(null)
				.url(urlDao.getUrl())
				.shortCode(this.generateUniqueIdentifier())
				.createdAt(urlDao.getCreatedAt())
				.updatedAt(urlDao.getUpdatedAt())
				.accesCount(urlDao.getAccesCount())
				.build();
		
	};
	
	private String generateUniqueIdentifier(){
		
		String idf = this.idGenerator.generateId();
		
		while(!this.urlRepo.getSameIdentifier(idf).isEmpty()){
			idf = this.idGenerator.generateId();
		}
		
		return idf;
		
	}

}
