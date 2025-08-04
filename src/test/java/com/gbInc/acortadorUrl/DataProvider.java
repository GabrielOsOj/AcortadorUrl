package com.gbInc.acortadorUrl;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataProvider {

	final private String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	public UrlIncoming getUrlIncomingMockEmptyOwner() {
		UrlIncoming url = new UrlIncoming();
		url.setUrl("https://www.youtube.com");
		url.setOwnerId("");

		return new UrlIncoming();
	}

	public UrlDao getUrlDaoMockEmptyOwner() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDao.builder()
			.id(1L)
			.url("https://www.youtube.com")
			.shortCode("shortcodeUnique")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.createdById("newRandomUrlGenerated")
			.accessCount(0)
			.build();
	}

	public UrlDataDTO getUrlIncomingMockEmptyOwnerDTO() {

		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDataDTO.builder()
			.id(1L)
			.url("https://www.youtube.com")
			.shortCode("shortcodeUnique")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.accesCount(0)
			.ownerId("newRandomUrlGenerated")
			.build();

	}

	///
	public UrlIncoming urlWithOwnerSaved() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("https://www.youtube.com");
		url.setOwnerId("08c7824b-b62c-4067-a80e-eb035bfe02a2");

		return new UrlIncoming();
	}

	public UrlDao getUrlEntityWithOwner() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		UrlDao urlNew = UrlDao.builder()
			.id(1l)
			.url("https://www.youtube.com")
			.shortCode("randomShortCode")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.accessCount(0)
			.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.build();

		return urlNew;
	}

	public UrlDataDTO getUrlDataDTOwithOwner() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		UrlDataDTO urlNew = UrlDataDTO.builder()
			.id(1l)
			.url("https://www.youtube.com")
			.shortCode("randomShortCode")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.accesCount(0)
			.ownerId("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.build();

		return urlNew;
	}

	///
	public UrlDao getUrlDaoMockExistsOwner() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDao.builder()
			.id(1L)
			.url("https://www.youtube.com")
			.shortCode("shortcodeUnique")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.accessCount(0)
			.build();
	}

	public String getShortedUrlSaved() {
		return "shortcodeUnique";
	}

	public UrlDao getUrlPreRetrieved() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDao.builder()
			.id(1L)
			.url("https://www.youtube.com")
			.shortCode("shortcodeUnique")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.accessCount(0)
			.build();
	}

	///
	public UrlIncoming getNewUrlToSave() {
		UrlIncoming url = new UrlIncoming();
		url.setUrl("https://www.instagram.com");
		url.setOwnerId("");

		return url;
	}

	public UrlDao getUrlSavedToEdit() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDao.builder()
			.id(1L)
			.url("https://www.youtube.com")
			.shortCode("shortcodeUnique")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.accessCount(4)
			.build();
	}

	public UrlDao getUrlSavedEdited() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDao.builder()
			.id(1L)
			.url("https://www.instagram.com")
			.shortCode("shortcodeUnique")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.accessCount(4)
			.build();
	}

	public UrlIncoming getNewUrlBlankUrl() {
		UrlIncoming url = new UrlIncoming();
		url.setUrl("");
		url.setOwnerId("08c7824b-b62c-4067-a80e-eb035bfe02a2");

		return url;
	}

	///
	public UrlDao getUrlToDelete() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDao.builder()
			.id(1L)
			.url("https://www.pinterest.com")
			.shortCode("uwWAasdloI")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.accessCount(4)
			.build();
	}

	///
	public UrlIncoming getUrlWithValidUser() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("");
		url.setOwnerId("08c7824b-b62c-4067-a80e-eb035bfe02a2");
		return url;
	}

	public UrlDao getDaoUrlWithStats() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDao.builder()
			.id(1L)
			.url("https://www.pinterest.com")
			.shortCode("uwWAasdloI")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.accessCount(4)
			.build();
	}

	public UrlDataDTO getDTOUrlWithStats() {
		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return UrlDataDTO.builder()
			.id(1L)
			.url("https://www.pinterest.com")
			.shortCode("uwWAasdloI")
			.createdAt(dateNow)
			.updatedAt(dateNow)
			.ownerId("08c7824b-b62c-4067-a80e-eb035bfe02a2")
			.accesCount(4)
			.build();
	}

	///
	public UrlIncoming getUrlWithNotValidUser() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("any");
		url.setOwnerId("");
		return url;
	}

}
