package com.gbInc.acortadorUrl.services;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.DataProvider;
import com.gbInc.acortadorUrl.exception.UrlException;
import com.gbInc.acortadorUrl.helper.UrlHelper;
import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import com.gbInc.acortadorUrl.persistence.repository.IurlRepository;
import com.gbInc.acortadorUrl.sevices.IurlService;
import com.gbInc.acortadorUrl.sevices.urlService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class urlServiceTest {

	@Mock
	private IurlRepository urlRepo;

	@Mock
	private UrlHelper urlHelper;

	@InjectMocks
	private urlService urlSv;

	private DataProvider dataP = new DataProvider();

	@Test
	public void saveUrlNewUserTest() {

		// given
		UrlIncoming newUrl = this.dataP.getUrlIncomingMockEmptyOwner();
		UrlDataDTO urlDTOnullVisitedCount = this.dataP.getUrlIncomingMockEmptyOwnerDTO();
		urlDTOnullVisitedCount.setAccesCount(null);
		// when

		when(this.urlHelper.isUrlValid(newUrl)).thenReturn(true);
		when(this.urlHelper.getUrlEntity(newUrl)).thenReturn(this.dataP.getUrlDaoMockEmptyOwner());

		when(this.urlHelper.toDTO(any())).thenReturn(this.dataP.getUrlIncomingMockEmptyOwnerDTO());

		UrlDataDTO urlDTO = this.urlSv.saveUrl(newUrl);
		ArgumentCaptor<UrlDao> capture = ArgumentCaptor.forClass(UrlDao.class);

		// then
		verify(this.urlRepo).save(capture.capture());
		assertEquals(this.dataP.getUrlDaoMockEmptyOwner().getShortCode(), capture.getValue().getShortCode());
		assertEquals(urlDTOnullVisitedCount.getShortCode(), urlDTO.getShortCode());

	}

	@Test
	public void saveUrlExistsOwner() {

		// given
		UrlIncoming urlWithOwner = this.dataP.urlWithOwnerSaved();
		// when
		when(this.urlHelper.isUrlValid(urlWithOwner)).thenReturn(true);
		when(this.urlHelper.getUrlEntity(urlWithOwner)).thenReturn(this.dataP.getUrlEntityWithOwner());
		when(this.urlHelper.toDTO(any())).thenReturn(this.dataP.getUrlDataDTOwithOwner());

		this.urlSv.saveUrl(urlWithOwner);
		ArgumentCaptor<UrlDao> capture = ArgumentCaptor.forClass(UrlDao.class);

		// then
		verify(this.urlRepo).save(capture.capture());
		assertEquals(this.dataP.getUrlEntityWithOwner().getCreatedById(), capture.getValue().getCreatedById());
	}

	@Test
	public void saveUrlBadUrlTest() {

		// given
		UrlIncoming newUrl = this.dataP.getUrlIncomingMockEmptyOwner();
		// when
		when(this.urlHelper.isUrlValid(newUrl)).thenReturn(false);
		// then
		assertThrows(UrlException.class, () -> {
			this.urlSv.saveUrl(newUrl);
		});
	}

	@Test
	public void retrieveUrlTest() {
		// given
		String savedShortUrl = this.dataP.getShortedUrlSaved();

		// when
		when(this.urlRepo.getSameIdentifier(anyString())).thenReturn(Optional.of(this.dataP.getUrlPreRetrieved()));
		this.urlSv.retrieveUrl(savedShortUrl);
		ArgumentCaptor<UrlDao> captured = ArgumentCaptor.forClass(UrlDao.class);
		// then

		verify(this.urlRepo).save(captured.capture());

	}

	@Test
	public void retrieveUrlNotExists() {
		// given
		String urlNotExists = "hello";
		// when
		when(this.urlRepo.getSameIdentifier(urlNotExists)).thenReturn(Optional.empty());
		// then
		assertThrows(UrlException.class, () -> {
			this.urlSv.retrieveUrl(urlNotExists);
		});
	}

	@Test
	public void updateUrl() {
		// given
		String urlSaved = "shortcodeUnique";
		UrlIncoming newUrl = this.dataP.getNewUrlToSave();

		// when
		when(this.urlHelper.isUrlValid(any())).thenReturn(true);
		when(this.urlRepo.getSameIdentifier("shortcodeUnique")).thenReturn(Optional.of(this.dataP.getUrlPreRetrieved()));
		when(this.urlHelper.updateUrlData(any(), any())).thenReturn(this.dataP.getUrlSavedEdited());

		this.urlSv.updateUrl(urlSaved, newUrl);
		ArgumentCaptor<UrlDao> capture = ArgumentCaptor.forClass(UrlDao.class);

		// then
		verify(this.urlRepo).save(capture.capture());
		assertEquals(newUrl.getUrl(), capture.getValue().getUrl());
		assertEquals(newUrl.getOwnerId(), capture.getValue().getCreatedById());
	}
	
	@Test
	public void updateUrlNotValidOwnerOnUrl() {
		// given
		String urlSaved = "shortcodeUnique";
		UrlIncoming newUrl = this.dataP.getNewUrlBadOwner();

		// when
	
		assertThrows(UrlException.class,()->{this.urlSv.updateUrl(urlSaved, newUrl);}
		);
	}

	@Test
	public void updateUrlBlankUrl() {
		// given
		String urlSaved = "helloWorld2";
		UrlIncoming newUrl = this.dataP.getNewUrlBlankUrl();
		// when
		assertThrows(UrlException.class, () -> {
			this.urlSv.updateUrl(urlSaved, newUrl);
		});
	}
	
	@Test
	public void updateUrlBadUrlIncoming() {
		// given
		String urlSaved = "helloWorld2";
		UrlIncoming newUrl = this.dataP.getNewUrlIncomingBadUrl();
		// when
		when(this.urlHelper.isUrlValid(any())).
				thenReturn(false);
		assertThrows(UrlException.class, () -> {
			this.urlSv.updateUrl(urlSaved, newUrl);
		});
	}

	@Test
	public void deleteUrlTest() {
		// given
		String userToken = "08c7824b-b62c-4067-a80e-eb035bfe02a2";
		String urlShort = "uwWAasdloI";
		// when
		when(this.urlRepo.getSameIdentifier(anyString())).thenReturn(Optional.of(this.dataP.getUrlToDelete()));
		this.urlSv.deleteUrl(userToken, urlShort);
		ArgumentCaptor<Long> capture = ArgumentCaptor.forClass(Long.class);
		// then
		verify(this.urlRepo).deleteById(capture.capture());
		assertEquals(this.dataP.getUrlToDelete().getId(), capture.getValue());
	}

	@Test
	public void deleteUrlTestBadUserId() {
		// given
		String userToken = "44wa7w8-284a-8745-af21-34ad2pa2487";
		String urlShort = "uwWAasdloI";
		// when
		when(this.urlRepo.getSameIdentifier(anyString())).thenReturn(Optional.of(this.dataP.getUrlToDelete()));

		assertThrows(UrlException.class, () -> {
			this.urlSv.deleteUrl(userToken, urlShort);
		});
	}

	@Test
	public void getUrlStatsTest() {
		// given
		String urlShort = "uwWAasdloI";
		// when
		when(this.urlRepo.getSameIdentifier(urlShort)).thenReturn(Optional.of(this.dataP.getDaoUrlWithStats()));
		when(this.urlHelper.toDTO(any())).thenReturn(this.dataP.getDTOUrlWithStats());

		UrlDataDTO urlSaved = this.urlSv.urlStats(urlShort);
		// then
		assertEquals(this.dataP.getDaoUrlWithStats().getCreatedById(), urlSaved.getOwnerId());
		assertEquals(this.dataP.getDaoUrlWithStats().getShortCode(), urlSaved.getShortCode());
		assertEquals(this.dataP.getDaoUrlWithStats().getUrl(), urlSaved.getUrl());
	}

	@Test
	public void getAllUrlsByOwner() {
		// given
		UrlIncoming validUrl = this.dataP.getUrlWithValidUser();
		// when
		when(this.urlRepo.getAllUrlsByOwnerId(anyString()))
			.thenReturn(Optional.of(List.of(this.dataP.getDaoUrlWithStats())));
		this.urlSv.getAllUrlsByOwner(validUrl);
		// then
		verify(this.urlRepo).getAllUrlsByOwnerId(validUrl.getOwnerId());

	}

	@Test
	public void getAllUrlsByOwnerBadOwnerId() {
		// given
		UrlIncoming NotValidUrl = this.dataP.getUrlWithNotValidUser();
		// when
		assertThrows(UrlException.class, () -> {
			this.urlSv.getAllUrlsByOwner(NotValidUrl);
		});

	}

}
