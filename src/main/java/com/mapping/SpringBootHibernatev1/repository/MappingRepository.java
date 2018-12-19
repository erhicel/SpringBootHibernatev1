package com.mapping.SpringBootHibernatev1.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mapping.SpringBootHibernatev1.model.Mapping;

// @CrossOrigin // Anotación para permitir requests desde un dominio diferente, y no es necesario el bean RestConfig de configuracion
@Repository
public interface MappingRepository extends PagingAndSortingRepository<Mapping, Long> {

	List<Mapping> findByProviderId(long providerId);

	Mapping findByValueIgnoreCase(String mappingProvider);

	Mapping findByProviderIdAndValue(long providerId, String mappingProvider);

}
