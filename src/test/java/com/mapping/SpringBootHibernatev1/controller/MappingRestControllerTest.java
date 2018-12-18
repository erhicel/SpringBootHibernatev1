package com.mapping.SpringBootHibernatev1.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapping.SpringBootHibernatev1.SpringBootHibernatev1Application;
import com.mapping.SpringBootHibernatev1.assembler.MappingResourceAssembler;
import com.mapping.SpringBootHibernatev1.model.Mapping;
import com.mapping.SpringBootHibernatev1.service.MappingService;

@RunWith(SpringRunner.class)
@WebMvcTest(MappingRestController.class)
@ContextConfiguration(classes = SpringBootHibernatev1Application.class, initializers = ConfigFileApplicationContextInitializer.class)
public class MappingRestControllerTest {

	@Autowired
	private WebApplicationContext		webApplicationContext;

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private MappingService				mappingService;

	@MockBean
	private MappingResourceAssembler	mappingResourceAssembler;

	@MockBean
	private ServletUriComponentsBuilder	servletUriComponentsBuilder;

	private Mapping						mockMapping	= new Mapping(687382L, 5L, 57L, "2", "Booked");

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetMappings() throws Exception {

		List<Mapping> mappings = new ArrayList<>();
		mappings.add(mockMapping);

		Mockito.when(mappingService.getAllMappingsByProvider(Mockito.anyLong())).thenReturn(mappings);

		Link mockLink = new Link("http://localhost/api/mappings/687382");
		mockMapping.add(mockLink);

		Resource resource = new Resource(mockMapping);

		Mockito.when(mappingResourceAssembler.toResource(Mockito.any(Mapping.class))).thenReturn(resource);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/mappings/providerId/57").contentType(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isOk()).andExpect(
						jsonPath("_embedded.mappings[0].id").value(mockMapping.getMappingId())).andExpect(
								jsonPath("_embedded.mappings[0].valueDescription").value(mockMapping.getValueDescription())).andExpect(
										jsonPath("_embedded.mappings[0]._links.self.href").value(
												"http://localhost/api/mappings/" + mockMapping.getMappingId()));

		Mockito.verify(mappingService, Mockito.times(1)).getAllMappingsByProvider(57);
		Mockito.verifyNoMoreInteractions(mappingService);

	}

	@Test
	public void testGetMappingByProviderAndMappingProvider() throws Exception {

		Mockito.when(mappingService.getMappingByProviderAndMappingProvider(Mockito.anyLong(), Mockito.anyString())).thenReturn(mockMapping);

		Link mockLink = new Link("http://localhost/api/mappings/687382");
		mockMapping.add(mockLink);

		Resource resource = new Resource(mockMapping);

		Mockito.when(mappingResourceAssembler.toResource(Mockito.any(Mapping.class))).thenReturn(resource);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/mappings/providerId/57/values?mappingProvider=CGISBzI5NzIzNjIaBzEzNjkyNjU=").contentType(
				MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isOk()).andExpect(
						jsonPath("id").value(mockMapping.getMappingId())).andExpect(
								jsonPath("valueDescription").value(mockMapping.getValueDescription())).andExpect(
										jsonPath("_links.self.href").value("http://localhost/api/mappings/" + mockMapping.getMappingId()));

		Mockito.verify(mappingService, Mockito.times(1)).getMappingByProviderAndMappingProvider(57, "CGISBzI5NzIzNjIaBzEzNjkyNjU=");
		Mockito.verifyNoMoreInteractions(mappingService);

	}

	@Test
	public void testGetMappingById() throws Exception {

		Mockito.when(mappingService.getMappingById(Mockito.any(Long.class))).thenReturn(mockMapping);

		Link mockLink = new Link("http://localhost/api/mappings/687382");
		mockMapping.add(mockLink);

		Resource resource = new Resource(mockMapping);

		Mockito.when(mappingResourceAssembler.toResource(Mockito.any(Mapping.class))).thenReturn(resource);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/mappings/687382").contentType(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isOk()).andExpect(
						jsonPath("id").value(mockMapping.getMappingId())).andExpect(
								jsonPath("valueDescription").value(mockMapping.getValueDescription())).andExpect(
										jsonPath("_links.self.href").value("http://localhost/api/mappings/687382"));

		Mockito.verify(mappingService, Mockito.times(1)).getMappingById(687382L);
		Mockito.verifyNoMoreInteractions(mappingService);

	}

	// @Test
	public void testAddMapping() throws Exception {

		Mockito.when(mappingService.addMapping(Mockito.any(Mapping.class))).thenReturn(mockMapping);

		Link mockLink = new Link("http://localhost/api/mappings/687382");
		mockMapping.add(mockLink);

		Resource resource = new Resource(mockMapping);

		Mockito.when(mappingResourceAssembler.toResource(Mockito.any(Mapping.class))).thenReturn(resource);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/mappings").contentType(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isCreated()).andExpect(
						jsonPath("mappingTypeId").value(mockMapping.getMappingTypeId())).andExpect(jsonPath("value").value(mockMapping.getValue())).andExpect(
								jsonPath("_links.self.href").value(containsString("http://localhost/api/mappings/")));

		Mockito.verify(mappingService, Mockito.times(1)).addMapping(Mockito.any(Mapping.class));
		Mockito.verifyNoMoreInteractions(mappingService);

	}

	@Test
	public void testAddMappingWithError() throws Exception {

		mockMapping.setMappingId(null);
		mockMapping.setMappingTypeId(0L);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/mappings").accept(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print()).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		Mockito.verify(mappingService, Mockito.times(0)).addMapping(Mockito.any(Mapping.class));
		Mockito.verifyNoMoreInteractions(mappingService);

	}

	@Test
	public void testAddMappingWithNotAllowOthersHttpMethods() throws Exception {

		mockMapping.setMappingId(null);
		mockMapping.setMappingTypeId(0L);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/mappings").accept(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isMethodNotAllowed()).andExpect(content().string("")).andDo(
				MockMvcResultHandlers.print()).andDo(MockMvcResultHandlers.print()).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());

		Mockito.verify(mappingService, Mockito.times(0)).addMapping(Mockito.any(Mapping.class));
		Mockito.verifyNoMoreInteractions(mappingService);

	}

	// @Test
	public void testDeleteMapping() throws Exception {

		Mockito.doNothing().when(mappingService).deleteMapping(Mockito.any(Long.class));

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/mappings/687595").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andDo(
				MockMvcResultHandlers.print());

	}

	// @Test
	public void testUpdateMapping() throws Exception {

		Mockito.when(mappingService.updateMapping(Mockito.any(Mapping.class))).thenReturn(mockMapping);

		Link mockLink = new Link("http://localhost/api/mappings/687382");
		mockMapping.add(mockLink);

		Resource resource = new Resource(mockMapping);

		Mockito.when(mappingResourceAssembler.toResource(Mockito.any(Mapping.class))).thenReturn(resource);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/mappings/687382").contentType(MediaType.APPLICATION_JSON).content(
				new ObjectMapper().writeValueAsString(mockMapping))).andExpect(status().isOk()).andExpect(
						jsonPath("id").value(mockMapping.getMappingId())).andExpect(jsonPath("mappingTypeId").value(mockMapping.getMappingTypeId())).andExpect(
								jsonPath("providerID").value(mockMapping.getProviderID())).andExpect(jsonPath("value").value(mockMapping.getValue())).andExpect(
										jsonPath("valueDescription").value(mockMapping.getValueDescription())).andExpect(
												jsonPath("_links.self.href").value("http://localhost/api/mappings/687382"))

				.andDo(MockMvcResultHandlers.print());

	}

}
