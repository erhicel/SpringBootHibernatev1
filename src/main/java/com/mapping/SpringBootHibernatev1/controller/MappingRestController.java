package com.mapping.SpringBootHibernatev1.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapping.SpringBootHibernatev1.assembler.MappingResourceAssembler;
import com.mapping.SpringBootHibernatev1.model.Mapping;
import com.mapping.SpringBootHibernatev1.service.MappingService;

@RestController
public class MappingRestController {

	@Autowired
	private MappingService				mappingService;

	@Autowired
	private MappingResourceAssembler	assembler;

	public void setEmployeeService(MappingService mappingService, MappingResourceAssembler assembler) {
		this.mappingService = mappingService;
		this.assembler = assembler;
	}

	@GetMapping("/api/mappings/providerId/{providerId}")
	public Resources<Resource<Mapping>> getMappings(@PathVariable(name = "providerId") Long providerId) {

		List<Resource<Mapping>> mappingsResource = new ArrayList<>();

		List<Mapping> mappings = mappingService.getAllMappingsByProvider(providerId);
		for (Mapping mapping : mappings) {
			Long mappingId = mapping.getIdMapping();
			// mapping.add(linkTo(methodOn(MappingRestController.class).getMappingById(mappingId)).withRel("get_user").withType("GET"));
			mappingsResource.add(assembler.toResource(mapping));
			// mapping.add(linkTo(methodOn(MappingRestController.class).deleteMapping(mappingId)).withRel("delete_user").withType("DELETE"));
		}
		Link link = linkTo(methodOn(MappingRestController.class).getMappings(providerId)).withSelfRel().withType("GET");
		// return new Resources<Mapping>(mappings, link);
		return new Resources<>(mappingsResource, link);
	}

	// @GetMapping("/api/mappings/providerValue/{mappingProviderValue}")
	// public Mapping getMapping(@PathVariable(name = "mappingProviderValue") String mappingProvider) {
	// return mappingService.getMapping(mappingProvider);
	// }

	// // @GetMapping("/api/mappings/{providerId}/{mappingProvider}")
	// @GetMapping("/api/mappings/providerId/{providerId}/mappingProvider/{mappingProvider}")
	// public Mapping getMappingByProviderAndMappingProvider(@PathVariable Long providerId, @PathVariable String mappingProvider) {
	// return mappingService.getMappingByProviderAndMappingProvider(providerId, mappingProvider);
	// }

	// @GetMapping("/api/mappings/{providerId}/{mappingProvider}")
	@GetMapping("/api/mappings/providerId/{providerId}/values")
	public Mapping getMappingByProviderAndMappingProvider(@PathVariable Long providerId, @RequestParam String mappingProvider) {
		return mappingService.getMappingByProviderAndMappingProvider(providerId, mappingProvider);
	}

	// @GetMapping("/api/mappings/{providerId}")
	// public Mapping getMappingByProviderAndMappingProvider(@PathVariable("providerId") Long providerId, @RequestParam("value") String value) {
	// return mappingService.getMappingByProviderAndMappingProvider(providerId, value);
	// }

	// @PostMapping("/api/mappings/mappingProvider")
	// public Mapping getMappingByProviderAndMappingProviderFromPost(Mapping mapping) {
	// return mappingService.getMappingByProviderAndMappingProvider(mapping.getMappingTypeId(), mapping.getValue());
	// }

	@GetMapping("/api/mappings/{mappingId}")
	public Mapping getMappingById(@PathVariable(name = "mappingId") Long mappingId) {
		return mappingService.getMappingById(mappingId);
	}

	// // @Valid annotation makes sure that the request body is valid
	// // @RequestBody annotation is used to bind the request body with a method parameter
	// @PostMapping(value = "/api/mappings")
	// public ResponseEntity<Object> addMapping(@Valid @RequestBody Mapping mapping) {
	// ResponseEntity<Object> response = mappingService.addMapping(mapping);
	// System.out.println("Mapping Saved Successfully");
	// return response;
	//
	// }

	// @PutMapping("/api/mappings/{mappingId}")
	// public ResponseEntity<Mapping> updateMapping(@RequestBody Mapping mapping, @PathVariable(name = "mappingId") Long mappingId) {
	// // ResponseEntity<Object> response = ResponseEntity.notFound().build();
	// // ResponseEntity<Mapping> mappingFound = mappingService.getMappingById(mappingId);
	// // if (mappingFound.getBody() != null) {
	// // mapping.setId(mappingId);
	//
	// // response = new ResponseEntity<>(mapping, HttpStatus.OK);
	// // }
	// return mappingService.updateMapping(mapping);
	// }

	@DeleteMapping("/api/mappings/{mappingId}")
	public void deleteMapping(@PathVariable("mappingId") Long mappingId) {

		mappingService.deleteMapping(mappingId);
		System.out.println("Employee Deleted Successfully");

	}

	// Ejemplo de save cuando se obtiene xml o se da como respuesta xml
	// @PostMapping(value = "/api/mappings", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {
	// MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	// public void saveMappingXml(@Valid @RequestBody Mapping mapping) {
	// mappingService.saveMapping(mapping);
	// System.out.println("Mapping Saved Successfully");
	// }

}
