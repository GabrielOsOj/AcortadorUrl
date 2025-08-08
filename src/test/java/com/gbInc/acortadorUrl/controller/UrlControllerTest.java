package com.gbInc.acortadorUrl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlErrorDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import com.gbInc.acortadorUrl.DataProvider;
import com.gbInc.acortadorUrl.exception.UrlExceptionConstants;
import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import com.gbInc.acortadorUrl.persistence.repository.IurlRepository;
import io.github.bucket4j.Bucket;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner.class)
public class UrlControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private Bucket bucket;

	@Autowired
	private IurlRepository urlRepo;

	private DataProvider dataP = new DataProvider();

	private ObjectMapper mapper;

	@BeforeEach
	public void init() {
		this.mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

		for (UrlDao u : this.dataP.getListOfUrlsToSave()) {
			this.urlRepo.save(u);
		}

	}

	@Test
	public void shortenTest() throws Exception {

		// given
		UrlIncoming urlIncoming = dataP.getNewCreatePetUrlIncoming();
		String url = "/urlShortener/shorten";
		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.post(url)
				.content(mapper.writeValueAsString(urlIncoming))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isAccepted())
			.andReturn();

		// then
		UrlDataDTO urlSaved = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlDataDTO.class);
		assertEquals(urlIncoming.getUrl(), urlSaved.getUrl());
	}

	@Test
	public void shortenTestEmptyUrl() throws Exception {

		// given
		UrlIncoming urlIncoming = dataP.getNewCreatePetUrlIncomingEmptyUrl();
		String url = "/urlShortener/shorten";
		// when
		when(bucket.tryConsume(1)).thenReturn(true);
		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.post(url)
				.content(mapper.writeValueAsString(urlIncoming))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andReturn();

		// then
		UrlErrorDTO urlErrorDTO = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);
		assertEquals(UrlExceptionConstants.BAD_REQUEST, urlErrorDTO.getMsg());
	}

	@Test
	public void shortenTestTooManyRequestsInShortTime() throws Exception {

		// given
		UrlIncoming urlIncoming = dataP.getNewCreatePetUrlIncoming();
		String url = "/urlShortener/shorten";
		// when
		when(bucket.tryConsume(1)).thenReturn(false);

		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.post(url)
				.content(mapper.writeValueAsString(urlIncoming))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isTooManyRequests())
			.andReturn();

		// then
		UrlErrorDTO urlErr = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);

		assertEquals(UrlExceptionConstants.TOO_MANY_REQUESTS, urlErr.getMsg());
		assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), urlErr.getCode());

	}

	@Test
	public void retrieveUrlTest() throws Exception {
		// given
		String url = "/urlShortener/adRfEEdEtW";
		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		this.mvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().is3xxRedirection());

	}

	@Test
	public void retrieveUrlTestUrlNotFound() throws Exception {
		// given
		String url = "/urlShortener/aeeeeeeee";
		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isNotFound()).andReturn();

		UrlErrorDTO urlError = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);

		assertEquals(HttpStatus.NOT_FOUND.value(), urlError.getCode());
		assertEquals(UrlExceptionConstants.NOT_FOUND, urlError.getMsg());
	}

	@Test
	public void editUrlTest() throws Exception {
		// given
		String urlShort = "adRfEEdEtW";
		String url = "/urlShortener/" + urlShort;
		UrlIncoming urlToEdit = this.dataP.getUrlIncomingToEdit();
		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.put(url)
				.content(this.mapper.writeValueAsString(urlToEdit))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		UrlDataDTO urlData = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlDataDTO.class);

		assertEquals(urlToEdit.getUrl(), urlData.getUrl());
		assertEquals(urlShort, urlData.getShortCode());

	}

	@Test
	public void editUrlTestUrlNotValidOwner() throws Exception {
		// given
		String urlShort = "adRfEEdEtW";
		String url = "/urlShortener/" + urlShort;
		UrlIncoming urlToEdit = this.dataP.getUrlIncomingToEditBadOwner();

		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.put(url)
				.content(this.mapper.writeValueAsString(urlToEdit))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andReturn();

		UrlErrorDTO urlError = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);

		assertEquals(UrlExceptionConstants.UNAUTHORIZED, urlError.getMsg());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), urlError.getCode());

	}
	
	@Test
	public void editUrlTestUrlNotFound() throws Exception {
		// given
		String urlShort = "aaaaaaaaaaaaa";
		String url = "/urlShortener/" + urlShort;
		UrlIncoming urlToEdit = this.dataP.getUrlIncomingToEdit();

		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.put(url)
				.content(this.mapper.writeValueAsString(urlToEdit))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andReturn();

		UrlErrorDTO urlError = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);

		assertEquals(UrlExceptionConstants.NOT_FOUND, urlError.getMsg());
		assertEquals(HttpStatus.NOT_FOUND.value(), urlError.getCode());

	}

	@Test
	public void deleteUrlTest() throws Exception {

		// given
		String urlToDelete = "/urlShortener/awd212311-b62c-4067-a80e-eb035bfe02a2/adRfEEdEtW";
		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		this.mvc.perform(MockMvcRequestBuilders.delete(urlToDelete)).andExpect(status().isNoContent());

		// then
		assertTrue(this.urlRepo.getSameIdentifier(urlToDelete).isEmpty());
	}

	@Test
	public void deleteUrlTestBadShortUrl() throws Exception {

		// given
		String urlToDelete = "/urlShortener/awd212311-b62c-4067-a80e-eb035bfe02a2/yuuuuuuuuu";
		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc.perform(MockMvcRequestBuilders.delete(urlToDelete))
			.andExpect(status().isNotFound())
			.andReturn();

		// then
		UrlErrorDTO urlError = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);

		assertEquals(UrlExceptionConstants.NOT_FOUND, urlError.getMsg());
		assertEquals(HttpStatus.NOT_FOUND.value(), urlError.getCode());
	}

	@Test
	public void deleteUrlTestUrlDoesNotBelongToTheUser() throws Exception {

		// given
		String urlToDelete = "/urlShortener/a051877da-b62c-4067-a80e-DDE2adf2A2d2/adRfEEdEtW";
		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc.perform(MockMvcRequestBuilders.delete(urlToDelete))
			.andExpect(status().isUnauthorized())
			.andReturn();

		// then
		UrlErrorDTO urlError = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);

		assertEquals(UrlExceptionConstants.UNAUTHORIZED, urlError.getMsg());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), urlError.getCode());
	}

	@Test
	public void getUrlStatsTest() throws Exception {
		// given
		String url = "/urlShortener/uwWAasdloI/stats";

		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();

		// then
		UrlDataDTO urlData = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlDataDTO.class);

		assertEquals(this.dataP.getListOfUrlsToSave().get(1).getShortCode(), urlData.getShortCode());

	}

	@Test
	public void getUrlStatsTestBadShortUrl() throws Exception {
		// given
		String url = "/urlShortener/uuuuuuuua/stats";

		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isNotFound()).andReturn();

		// then
		UrlErrorDTO urlError = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);

		assertEquals(UrlExceptionConstants.NOT_FOUND, urlError.getMsg());
		assertEquals(HttpStatus.NOT_FOUND.value(), urlError.getCode());

	}

	@Test
	public void getAllUrlsByOwnerTest() throws Exception {

		// given
		UrlIncoming urlWithValidOwner = this.dataP.getUrlIncomingWithRealOwner();
		String url = "/urlShortener/getAll";

		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.post(url)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(urlWithValidOwner))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		List<UrlDataDTO> allUrls = this.mapper.readValue(resp.getResponse().getContentAsString(),
				new TypeReference<List<UrlDataDTO>>() {
				});

		assertEquals(2, allUrls.size());
		assertEquals(urlWithValidOwner.getOwnerId(), allUrls.getFirst().getOwnerId());
		assertEquals(urlWithValidOwner.getOwnerId(), allUrls.getLast().getOwnerId());

	}

	@Test
	public void getAllUrlsByOwnerTestEmptyUserId() throws Exception {

		// given
		when(bucket.tryConsume(1)).thenReturn(true);

		UrlIncoming urlWithValidOwner = this.dataP.getEmptyUrlIncoming();
		String url = "/urlShortener/getAll";

		// when
		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.post(url)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(urlWithValidOwner))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andReturn();

		// then
		UrlErrorDTO urlError = this.mapper.readValue(resp.getResponse().getContentAsString(), UrlErrorDTO.class);

		assertEquals(UrlExceptionConstants.BAD_OWNER_ID, urlError.getMsg());
		assertEquals(HttpStatus.BAD_REQUEST.value(), urlError.getCode());

	}

	@Test
	public void getAllUrlsByOwnerTestUserHasntUrls() throws Exception {

		UrlIncoming urlWithValidOwner = this.dataP.getUrlIncomingWithUserHasntUrls();
		String url = "/urlShortener/getAll";

		// when
		when(bucket.tryConsume(1)).thenReturn(true);

		MvcResult resp = this.mvc
			.perform(MockMvcRequestBuilders.post(url)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(urlWithValidOwner))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		List<UrlDataDTO> allUrls = this.mapper.readValue(resp.getResponse().getContentAsString(),
				new TypeReference<List<UrlDataDTO>>() {
				});

		assertEquals(0, allUrls.size());

	}

}
