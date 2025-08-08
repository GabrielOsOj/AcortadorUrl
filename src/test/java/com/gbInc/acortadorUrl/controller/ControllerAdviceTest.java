package com.gbInc.acortadorUrl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gbInc.acortadorUrl.DTO.UrlErrorDTO;
import com.gbInc.acortadorUrl.exception.UrlExceptionConstants;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner.class)
public class ControllerAdviceTest {

	@Autowired
	private MockMvc mvc;

	private ObjectMapper mapper;

	@BeforeEach
	public void init() {
		this.mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
	}

	@Test
	public void errorDataTipeHandlerTest() throws Exception{
		
		//given
		String url = "/urlShortener/uwWAasdloI";
		
		//when
		MvcResult resp = this.mvc.perform(MockMvcRequestBuilders.put(url)
		.content("badTipeContent")
		.contentType(MediaType.TEXT_PLAIN))
		.andExpect(status().isBadRequest())
		.andReturn();
		
		//then
		UrlErrorDTO urlErr = this.mapper.readValue(resp.getResponse().getContentAsString(),UrlErrorDTO.class);
		assertEquals(UrlExceptionConstants.BAD_REQUEST, urlErr.getMsg());
		assertEquals(HttpStatus.BAD_REQUEST.value(), urlErr.getCode());
		
	}
	
		@Test
	public void errorNotFoundResourceExceptionTest() throws Exception{
		
		//given
		String url = "/urlShort/NotExistsUrl";
		
		//when
		MvcResult resp = this.mvc.perform(MockMvcRequestBuilders.get(url))
		.andExpect(status().isNotFound())
		.andReturn();
		
		//then
		UrlErrorDTO urlErr = this.mapper.readValue(resp.getResponse().getContentAsString(),UrlErrorDTO.class);
		assertEquals(UrlExceptionConstants.BAD_RESOURCE, urlErr.getMsg());
		assertEquals(HttpStatus.NOT_FOUND.value(), urlErr.getCode());
		
	}

}
