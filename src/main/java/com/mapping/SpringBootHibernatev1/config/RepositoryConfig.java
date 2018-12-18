package com.mapping.SpringBootHibernatev1.config;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;

import com.mapping.SpringBootHibernatev1.model.Mapping;

// Esta clase se agrega para mostrar los ID's en los json
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		// Una clase o varias (Mapping.class, Provider.class)
		config.exposeIdsFor(Mapping.class);

		// Al entities
		config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));

		// Con las siguientes instrucciones la salida es de tipo json en vez de hal+json
		config.setDefaultMediaType(MediaType.APPLICATION_JSON);
		config.useHalAsDefaultJsonMediaType(false);
	}
}
