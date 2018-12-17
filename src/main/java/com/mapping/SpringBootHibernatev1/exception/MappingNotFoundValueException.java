package com.mapping.SpringBootHibernatev1.exception;

public class MappingNotFoundValueException extends RuntimeException {

	public MappingNotFoundValueException(String mappingValue) {
		super("could not find mapping with value: '" + mappingValue + "'");
	}

}
