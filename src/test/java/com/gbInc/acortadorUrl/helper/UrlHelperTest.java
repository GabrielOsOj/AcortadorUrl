package com.gbInc.acortadorUrl.helper;

import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.DataProvider;
import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import com.gbInc.acortadorUrl.persistence.repository.IurlRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlHelperTest {

	@Mock
	private IurlRepository urlRepo;

	@InjectMocks
	private UrlHelper urlHelper;

	public DataProvider dataP = new DataProvider();

	@Test
	public void getUrlEntityTest() {
		// given
		UrlIncoming urlIncoming = this.dataP.getNewCreatePetUrlIncoming();
		// when
		when(this.urlRepo.getFirstUrlByOwner(anyString())).thenReturn(Optional.empty());

		UrlDao urlResp = this.urlHelper.getUrlEntity(urlIncoming);
		// then
		assertNotEquals(urlIncoming.getOwnerId(), urlResp.getCreatedById());
	}

	@Test
	public void getUrlEntityTestSetIdFromSavedUser() {
		// given
		UrlIncoming urlIncoming = this.dataP.getUrlIncomingWithRealOwner();
		urlIncoming.setUrl("http://testUrl");
		// when
		when(this.urlRepo.getFirstUrlByOwner(anyString()))
			.thenReturn(Optional.of(this.dataP.getListOfUrlsToSave().getLast()));

		UrlDao urlResp = this.urlHelper.getUrlEntity(urlIncoming);
		// then
		assertEquals(urlIncoming.getOwnerId(), urlResp.getCreatedById());
	}

	@Test
	public void getUrlEntityRegenerateUniqueId(){
		//given
		UrlIncoming url = this.dataP.getNewCreatePetUrlIncoming();
		//when
		when(this.urlRepo.getSameIdentifier(anyString()))
				.thenReturn(Optional.of(this.dataP.getListOfUrlsToSave().get(0)))
				.thenReturn(Optional.of(this.dataP.getListOfUrlsToSave().get(1)))
				.thenReturn(Optional.empty());
		
		UrlDao res = this.urlHelper.getUrlEntity(url);
		
		//then
		assertNotEquals(this.dataP.getListOfUrlsToSave().get(0).getShortCode(), res.getShortCode());
		assertNotEquals(this.dataP.getListOfUrlsToSave().get(1).getShortCode(), res.getShortCode());
		
	}
}
