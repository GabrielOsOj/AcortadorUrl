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
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UrlHelper {

	final private String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	final private String URL_REGEX_EXAMPLE = "^https?:\\/\\/.+$";

	@Autowired
	IurlRepository urlRepo;

	IdGenerator idGenerator;

	public UrlHelper() {
		this.idGenerator = new IdGenerator();
	}

	public UrlDao getUrlEntity(UrlIncoming urlLong) {

		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		String ownerId = this.getOwnerId(urlLong.getOwnerId());

		UrlDao urlNew = UrlDao.builder()
			.id(null)
			.url(urlLong.getUrl())
			.shortCode(this.generateUniqueIdentifier())
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.accessCount(0)
			.createdById(ownerId)
			.build();

		return urlNew;

	}

	public boolean isUrlValid(UrlIncoming url) {

		return Pattern.compile(this.URL_REGEX_EXAMPLE).matcher(url.getUrl()).find();

	}

	public UrlDao updateUrlData(UrlDao urlSaved, String newUrl) {
		this.updateUrl(urlSaved, newUrl);
		this.updateDate(urlSaved);
		return urlSaved;
	}

	private UrlDao updateUrl(UrlDao urlDao, String newUrl) {
		return urlDao.setUrl(newUrl);
	}

	private UrlDao updateDate(UrlDao urlDao) {
		return urlDao.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT)));
	}

	public UrlDataDTO toDTO(UrlDao urlDao) {

		return UrlDataDTO.builder()
			.id(null)
			.url(urlDao.getUrl())
			.shortCode(urlDao.getShortCode())
			.createdAt(urlDao.getCreatedAt())
			.updatedAt(urlDao.getUpdatedAt())
			.accesCount(urlDao.getAccessCount())
			.ownerId(urlDao.getCreatedById())
			.build();

	};

	private String generateUniqueIdentifier() {

		String idf = this.idGenerator.generateId();

		while (!this.urlRepo.getSameIdentifier(idf).isEmpty()) {
			idf = this.idGenerator.generateId();
		}

		return idf;

	}

	private String getOwnerId(String ownerId) {

		Optional<UrlDao> urlSaved = this.urlRepo.getFirstUrlByOwner(ownerId);

		if (urlSaved.isEmpty()) {
			return this.idGenerator.generatedOwnerId();
		}

		return urlSaved.get().getCreatedById();

	}

}
