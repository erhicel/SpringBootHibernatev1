package com.mapping.SpringBootHibernatev1.service;

import java.util.List;

import com.mapping.SpringBootHibernatev1.model.Mapping;

public interface MappingService {

	public List<Mapping> getAllMappingsByProvider(long providerId);

	public Mapping getMapping(String mappingProvider);

	public Mapping getMappingByProviderAndMappingProvider(long providerId, String mappingProvider);

	public Mapping addMapping(Mapping mapping);

	public Mapping getMappingById(Long mappingId);

	public Mapping updateMapping(Mapping mapping);

	void deleteMapping(Long mappingId);

}
