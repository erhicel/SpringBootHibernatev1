package com.mapping.SpringBootHibernatev1.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<Resources<Resource<Mapping>>> getMappings(@PathVariable(name = "providerId") Long providerId) {

		List<Resource<Mapping>> mappingsResource = new ArrayList<>();

		for (Mapping mapping : mappingService.getAllMappingsByProvider(providerId)) {
			mappingsResource.add(assembler.toResource(mapping));
		}
		Link link = linkTo(methodOn(MappingRestController.class).getMappings(providerId)).withSelfRel().withType("GET");
		return ResponseEntity.ok(new Resources<>(mappingsResource, link));
	}

	@GetMapping("/api/mappings/providerValue/{mappingProviderValue}")
	public ResponseEntity<Resource<Mapping>> getMapping(@PathVariable(name = "mappingProviderValue") String mappingProvider) {
		return ResponseEntity.ok(assembler.toResource(mappingService.getMapping(mappingProvider)));
	}

	// // @GetMapping("/api/mappings/{providerId}/{mappingProvider}")
	// @GetMapping("/api/mappings/providerId/{providerId}/mappingProvider/{mappingProvider}")
	// public Mapping getMappingByProviderAndMappingProvider(@PathVariable Long providerId, @PathVariable String mappingProvider) {
	// return mappingService.getMappingByProviderAndMappingProvider(providerId, mappingProvider);
	// }

	// @GetMapping("/api/mappings/{providerId}/{mappingProvider}")
	@GetMapping("/api/mappings/providerId/{providerId}/values")
	public ResponseEntity<Resource<Mapping>> getMappingByProviderAndMappingProvider(@PathVariable Long providerId, @RequestParam String mappingProvider) {
		return ResponseEntity.ok(assembler.toResource(mappingService.getMappingByProviderAndMappingProvider(providerId, mappingProvider)));
	}

	@GetMapping("/api/mappings/{mappingId}")
	public ResponseEntity<Resource<Mapping>> getMappingById(@PathVariable(name = "mappingId") Long mappingId) {
		return ResponseEntity.ok(assembler.toResource(mappingService.getMappingById(mappingId)));
	}

	// // @Valid annotation makes sure that the request body is valid
	// // @RequestBody annotation is used to bind the request body with a method parameter
	@PostMapping(value = "/api/mappings")
	public ResponseEntity<Object> addMapping(@Valid @RequestBody Mapping mapping) {
		Mapping response = mappingService.addMapping(mapping);
		System.out.println("Mapping Saved Successfully");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).body(assembler.toResource(response));

	}

	@PutMapping("/api/mappings/{mappingId}")
	public ResponseEntity<Resource<Mapping>> updateMapping(@RequestBody Mapping mapping, @PathVariable(name = "mappingId") Long mappingId) {
		// ResponseEntity<Object> response = ResponseEntity.notFound().build();
		// ResponseEntity<Mapping> mappingFound = mappingService.getMappingById(mappingId);
		// if (mappingFound.getBody() != null) {
		// mapping.setId(mappingId);

		// response = new ResponseEntity<>(mapping, HttpStatus.OK);
		// }
		return ResponseEntity.ok(assembler.toResource(mappingService.updateMapping(mapping)));
	}

	@DeleteMapping("/api/mappings/{mappingId}")
	public ResponseEntity<Object> deleteMapping(@PathVariable("mappingId") Long mappingId) {

		mappingService.deleteMapping(mappingId);
		System.out.println("Employee Deleted Successfully");
		return ResponseEntity.noContent().build();

	}

	// Ejemplo de save cuando se obtiene xml o se da como respuesta xml
	// @PostMapping(value = "/api/mappings", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {
	// MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	// public void saveMappingXml(@Valid @RequestBody Mapping mapping) {
	// mappingService.saveMapping(mapping);
	// System.out.println("Mapping Saved Successfully");
	// }

}
