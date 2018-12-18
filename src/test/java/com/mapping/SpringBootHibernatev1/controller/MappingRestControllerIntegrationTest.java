package com.mapping.SpringBootHibernatev1.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapping.SpringBootHibernatev1.SpringBootHibernatev1Application;
import com.mapping.SpringBootHibernatev1.exception.MappingNotFoundException;
import com.mapping.SpringBootHibernatev1.model.Mapping;
import com.mapping.SpringBootHibernatev1.service.MappingService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = SpringBootHibernatev1Application.class)
@AutoConfigureMockMvc
public class MappingRestControllerIntegrationTest {

	@Autowired
	private WebApplicationContext		webApplicationContext;

	@Autowired
	private MockMvc						mockMvc;

	@Autowired
	private MappingService				mappingService;

	@MockBean
	private ServletUriComponentsBuilder	servletUriComponentsBuilder;

	private Mapping						mockMapping	= new Mapping(687382L, 5L, 57L, "2", "Booked");

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetMappings() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/mappings/providerId/57").contentType(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isOk()).andExpect(jsonPath("content", hasSize(117))).andExpect(
						jsonPath("content[0].id").value(mockMapping.getMappingId())).andExpect(
								jsonPath("content[0].valueDescription").value(mockMapping.getValueDescription())).andExpect(
										jsonPath("content[0].links[0].href").value("http://localhost/api/mappings/687382"));

	}

	@Test
	public void testGetMappingByProviderAndMappingProvider() throws Exception {

		Mapping mockMapping = new Mapping(687484L, 1L, 57L, "CGISBzI5NzIzNjIaBzEzNjkyNjU=", "Club Junior Suite*");

		mockMvc.perform(MockMvcRequestBuilders.get("/api/mappings/providerId/57/values?mappingProvider=CGISBzI5NzIzNjIaBzEzNjkyNjU=").contentType(
				MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isOk()).andExpect(
						jsonPath("id").value(mockMapping.getMappingId())).andExpect(
								jsonPath("valueDescription").value(mockMapping.getValueDescription())).andExpect(
										jsonPath("links[0].href").value("http://localhost/api/mappings/687484"));

	}

	@Test
	public void testGetMappingById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/mappings/687382").contentType(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isOk()).andExpect(
						jsonPath("id").value(mockMapping.getMappingId())).andExpect(
								jsonPath("valueDescription").value(mockMapping.getValueDescription())).andExpect(
										jsonPath("links[0].href").value("http://localhost/api/mappings/687382"));

	}

	@Test
	public void testGetMappingByIdWithNotFoundStatus() throws Exception {

		org.assertj.core.api.Assertions.assertThatThrownBy(
				() -> mockMvc.perform(MockMvcRequestBuilders.get("/api/mappings/6873888").contentType(MediaType.APPLICATION_JSON)).andExpect(
						status().isNotFound())).hasCause(new MappingNotFoundException(6873888L));

	}

	// @Test
	public void testAddMapping() throws Exception {

		mockMapping.setMappingId(null);
		mockMapping.setValueDescription("testRoomJumbo");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/mappings").contentType(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isCreated()).andExpect(
						jsonPath("mappingTypeId").value(mockMapping.getMappingTypeId())).andExpect(jsonPath("value").value(mockMapping.getValue())).andExpect(
								jsonPath("links[0].href").value(containsString("http://localhost/api/mappings/")));

	}

	// @Test
	public void testUpdateMapping() throws Exception {

		Mapping mockMapping = new Mapping(687591L, 1L, 57L, "testRoomTest", "testRoomJumbo");

		mockMvc.perform(MockMvcRequestBuilders.put("/api/mappings/687591").contentType(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isOk()).andExpect(
						jsonPath("id").value(mockMapping.getMappingId())).andExpect(jsonPath("mappingTypeId").value(mockMapping.getMappingTypeId())).andExpect(
								jsonPath("providerID").value(mockMapping.getProviderID())).andExpect(jsonPath("value").value(mockMapping.getValue())).andExpect(
										jsonPath("valueDescription").value(mockMapping.getValueDescription())).andExpect(
												jsonPath("links[0].href").value("http://localhost/api/mappings/687591"))

				.andDo(MockMvcResultHandlers.print());

	}

	// @Test
	public void testDeleteMapping() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/mappings/687591").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andDo(
				MockMvcResultHandlers.print());

	}

}
