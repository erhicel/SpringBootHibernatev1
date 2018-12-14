package com.mapping.SpringBootHibernatev1.exception;

public class MappingNotFoundException extends RuntimeException {

	public MappingNotFoundException(Long mappingId) {
		super("could not find mapping with Id: '" + mappingId + "'");
	}

}
