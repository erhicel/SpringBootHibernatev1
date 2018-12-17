package com.mapping.SpringBootHibernatev1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.stereotype.Service;

import com.mapping.SpringBootHibernatev1.assembler.MappingResourceAssembler;
import com.mapping.SpringBootHibernatev1.exception.MappingNotFoundException;
import com.mapping.SpringBootHibernatev1.exception.MappingNotFoundValueException;
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

		List<Mapping> mappings = mappingRepository.findByProviderId(providerId);

		if (mappings.isEmpty())
			throw new MappingNotFoundException(providerId);
		return mappings;
	}

	@Override
	public Mapping getMapping(String mappingProvider) {

		Mapping mapping = mappingRepository.findByValueIgnoreCase(mappingProvider);

		if (mapping == null)
			throw new MappingNotFoundValueException(mappingProvider);

		return mapping;

	}

	@Override
	public Mapping getMappingByProviderAndMappingProvider(long providerId, String mappingProvider) {

		Mapping mapping = mappingRepository.findByProviderIdAndValue(providerId, mappingProvider);

		if (mapping == null)
			throw new MappingNotFoundValueException(mappingProvider);

		return mapping;

	}

	@Override
	public Mapping addMapping(Mapping mapping) {
		return mappingRepository.save(mapping);

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
	public Mapping updateMapping(Mapping mapping) {

		if (!mappingRepository.findById(mapping.getIdMapping()).isPresent())
			throw new MappingNotFoundException(mapping.getIdMapping());

		return mappingRepository.save(mapping);

	}

	@Override
	public void deleteMapping(Long mappingId) {

		if (!mappingRepository.findById(mappingId).isPresent())
			throw new MappingNotFoundException(mappingId);

		mappingRepository.deleteById(mappingId);
	}

}
