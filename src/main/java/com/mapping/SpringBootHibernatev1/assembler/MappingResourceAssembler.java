package com.mapping.SpringBootHibernatev1.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.mapping.SpringBootHibernatev1.controller.MappingRestController;
import com.mapping.SpringBootHibernatev1.model.Mapping;

@Component
public class MappingResourceAssembler implements ResourceAssembler<Mapping, Resource<Mapping>> {

	@Override
	public Resource<Mapping> toResource(Mapping mapping) {

		return new Resource<>(mapping, linkTo(methodOn(MappingRestController.class).getMappingById(mapping.getIdMapping())).withSelfRel());
	}

}
