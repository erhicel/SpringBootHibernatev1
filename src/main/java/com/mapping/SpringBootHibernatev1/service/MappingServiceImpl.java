package com.mapping.SpringBootHibernatev1.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mapping.SpringBootHibernatev1.assembler.MappingResourceAssembler;
import com.mapping.SpringBootHibernatev1.exception.MappingNotFoundException;
import com.mapping.SpringBootHibernatev1.model.Mapping;
import com.mapping.SpringBootHibernatev1.repository.MappingRepository;

@Service
public class MappingServiceImpl implements MappingService {

	@Autowired

	private MappingRepository		mappingRepository;

	@Autowired
	MappingResourceAssembler		assembler;

	@Autowired
	private RepositoryEntityLinks	entityLinks;

	public void setMappingRepository(MappingRepository mappingRepository, MappingResourceAssembler assembler) {
		this.mappingRepository = mappingRepository;
		this.assembler = assembler;
	}

	@Override
	public List<Mapping> getAllMappingsByProvider(long providerId) {

		// // List<String> result = new ArrayList<String>();
		//
		// // List<Mapping> mappings = mappingRepository.findByProviderId(providerId);
		//
		// // for (Mapping mapping : mappings) {
		// //
		// // result.add(mapping.getValueDescription());
		// //
		// // }
		//
		// List<Mapping> mappings = mappingRepository.findByProviderId(providerId);
		//
		// Resources<Mapping> mappingsResources;
		//
		// for (Mapping mapping : mappings) {
		// // Link selfLink = linkTo(methodOn(MappingServiceImpl.class)
		// // .getMappingById(customerId, order.getOrderId())).withSelfRel();
		// // Link selfLink = null;
		// // URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mapping.getId()).toUri();
		// // mapping.add(selfLink);
		//// Long mappingId = mapping.getIdMapping();
		// ResponseEntity<Mapping> methodLinkBuilder = ControllerLinkBuilder
		// .methodOn(MappingRestController.class).getMappingById(mapping.getIdMapping());
		// mapping.add(
		// entityLinks.linkToSingleResource(methodOn(MappingRestController.class).getMappingById(mappingId)).withRel("get_mapping").withType("GET"));
		// // mapping.add(linkTo(methodOn(MappingServiceImpl.class).deleteMapping(mappingId)).withRel("delete_mapping").withType("DELETE"));
		// }
		//
		// Link link = linkTo(methodOn(MappingServiceImpl.class).getAllMappingsByProvider(providerId)).withSelfRel().withType("GET");
		return mappingRepository.findByProviderId(providerId);
	}

	@Override
	public Mapping getMapping(String mappingProvider) {

		return mappingRepository.findByValueIgnoreCase(mappingProvider);

	}

	@Override
	public Mapping getMappingByProviderAndMappingProvider(long providerId, String mappingProvider) {

		return mappingRepository.findByProviderIdAndValue(providerId, mappingProvider);

	}

	@Override
	public ResponseEntity<Object> addMapping(Mapping mapping) {
		Mapping savedMapping = mappingRepository.save(mapping);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMapping.getIdMapping()).toUri();

		// return ResponseEntity.created(URI.create("/admin/mappings/" + savedMapping.getId())).body(savedMapping);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(location);

		return new ResponseEntity<>(headers, HttpStatus.CREATED);

		// return new ResponseEntity<Object>(HttpStatus.CREATED);

		// return ResponseEntity.created(location).build();

	}

	@Override
	public Mapping getMappingById(Long mappingId) {

		// 3 formas de obtener el mapping por id

		// Primera forma
		// Mapping mapping = null;
		// // Get optional object because maybe the mapping with mappingId there isn't
		// Optional<Mapping> optEmp = mappingRepository.findById(mappingId);
		// if (optEmp.isPresent()) {
		// mapping = optEmp.get();
		// } else {
		// throw new MappingNotFoundException(mappingId);
		// }

		// Segunda forma
		// return mappingRepository.findById(mappingId).map(mapping -> new ResponseEntity<>(mapping, HttpStatus.OK)).orElseThrow(
		// () -> new MappingNotFoundException(mappingId));

		// Tercera forma
		return mappingRepository.findById(mappingId).orElseThrow(() -> new MappingNotFoundException(mappingId));
	}

	@Override
	public ResponseEntity<Mapping> updateMapping(Mapping mapping) {
		// En este caso se tienen que crear el json con todos los valores
		// del mapping para poder modificar los valores correspondiente, incluyendo el id
		// {
		// "id": 687521,
		// "mappingTypeId": 1,
		// "providerID": 57,
		// "value": "testRoomProvider",
		// "valueDescription": "testRoomJumbo"
		// }

		mappingRepository.save(mapping);

		return new ResponseEntity<>(mapping, HttpStatus.OK);

	}

	@Override
	public void deleteMapping(Long mappingId) {
		mappingRepository.deleteById(mappingId);
	}

}
