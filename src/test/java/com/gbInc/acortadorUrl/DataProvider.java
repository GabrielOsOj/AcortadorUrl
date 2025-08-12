package com.gbInc.acortadorUrl;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
		url.setOwnerId("08c7824b-b62c-4067-a80e-eb035bfe02a2");

		return url;
	}
	
		public UrlIncoming getNewUrlBadOwner() {
		UrlIncoming url = new UrlIncoming();
		url.setUrl("https://www.instagram.com");
		url.setOwnerId("a80e-eb035bfe02a2");

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
	
		public UrlIncoming getNewUrlIncomingBadUrl() {
		UrlIncoming url = new UrlIncoming();
		url.setUrl("urlNotValid");
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

	/// for integration tests
	public UrlIncoming getNewCreatePetUrlIncoming() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("http://www.tiktok.com");
		url.setOwnerId("");
		return url;

	}

	public UrlIncoming getNewCreatePetUrlIncomingEmptyUrl() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("");
		url.setOwnerId("");
		return url;

	}

	public List<UrlDao> getListOfUrlsToSave() {

		String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(this.DATE_FORMAT));

		return List.of(
				UrlDao.builder()
					.id(null)
					.url("https://www.instagram.com")
					.shortCode("adRfEEdEtW")
					.createdAt(dateNow)
					.updatedAt(dateNow)
					.createdById("awd212311-b62c-4067-a80e-eb035bfe02a2")
					.accessCount(0)
					.build(),
				UrlDao.builder()
					.id(null)
					.url("https://www.pinterest.com")
					.shortCode("uwWAasdloI")
					.createdAt(dateNow)
					.updatedAt(dateNow)
					.createdById("a051877da-b62c-4067-a80e-DDE2adf2A2d2")
					.accessCount(0)
					.build(),
				UrlDao.builder()
					.id(null)
					.url("https://x.com")
					.shortCode("awREwdafGR")
					.createdAt(dateNow)
					.updatedAt(dateNow)
					.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
					.accessCount(0)
					.build(),
				UrlDao.builder()
					.id(null)
					.url("https://www.pinterest.com")
					.shortCode("TREwqSAdFW")
					.createdAt(dateNow)
					.updatedAt(dateNow)
					.createdById("08c7824b-b62c-4067-a80e-eb035bfe02a2")
					.accessCount(0)
					.build()

		);
	}

	///
	public UrlIncoming getUrlIncomingToEdit() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("http://www.instagram.com.edited");
		url.setOwnerId("awd212311-b62c-4067-a80e-eb035bfe02a2");
		return url;

	}
	
		public UrlIncoming getUrlIncomingToEditBadOwner() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("http://www.instagram.com.edited");
		url.setOwnerId("4067-a80e-eb035bfe02a2");
		return url;

	}

	public UrlIncoming getUrlIncomingWithRealOwner() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("");
		url.setOwnerId("08c7824b-b62c-4067-a80e-eb035bfe02a2");
		return url;

	}
	
	public UrlIncoming getEmptyUrlIncoming() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("");
		url.setOwnerId("");
		return url;
		
	}
	
	
	public UrlIncoming getUrlIncomingWithUserHasntUrls() {

		UrlIncoming url = new UrlIncoming();
		url.setUrl("");
		url.setOwnerId("08c7824b-4067-b62c-a80e-a051877da");
		return url;
		
	}

}
