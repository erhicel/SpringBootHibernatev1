package com.mapping.SpringBootHibernatev1.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mapping.SpringBootHibernatev1.model.Mapping;

@Repository
public interface MappingRepository extends PagingAndSortingRepository<Mapping, Long> {

	List<Mapping> findByProviderId(long providerId);

	Mapping findByValueIgnoreCase(String mappingProvider);

	Mapping findByProviderIdAndValue(long providerId, String mappingProvider);

}
