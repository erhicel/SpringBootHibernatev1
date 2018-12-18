package com.mapping.SpringBootHibernatev1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Mapping extends ResourceSupport {

	@Id
	@Column(name = "ID")
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	mappingId;

	@NotNull
	@Valid
	@Positive
	@Column(name = "MAPPING_TYPE_ID")
	private Long	mappingTypeId;

	@NotNull
	@Valid
	@Positive
	@Column(name = "PROVIDER_ID")
	private Long	providerId;

	@NotNull
	@Valid
	@Size(min = 1, message = "Value should have atleast 1 characters")
	@Column(name = "VALUE")
	private String	value;

	@NotNull
	@Valid
	@Size(min = 1, message = "Value description should have atleast 1 characters")
	@Column(name = "VALUE_DESCRIPTION")
	private String	valueDescription;

	public Mapping() {}

	public Mapping(Long id, Long mappingTypeId, Long providerId, String value, String valueDescription) {
		super();
		this.mappingId = id;
		this.mappingTypeId = mappingTypeId;
		this.providerId = providerId;
		this.value = value;
		this.valueDescription = valueDescription;
	}

	public Long getMappingId() {
		return mappingId;
	}

	// @JsonIgnore se utiliza si no se quiere setear este valor
	public void setMappingId(Long id) {
		this.mappingId = id;
	}

	public Long getMappingTypeId() {
		return mappingTypeId;
	}

	public void setMappingTypeId(Long mappingTypeId) {
		this.mappingTypeId = mappingTypeId;
	}

	public Long getProviderID() {
		return providerId;
	}

	public void setProviderID(Long providerID) {
		this.providerId = providerID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueDescription() {
		return valueDescription;
	}

	public void setValueDescription(String valueDescription) {
		this.valueDescription = valueDescription;
	}

}
