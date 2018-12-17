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

@Entity
public class Mapping extends ResourceSupport {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	idMapping;

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
		this.idMapping = id;
		this.mappingTypeId = mappingTypeId;
		this.providerId = providerId;
		this.value = value;
		this.valueDescription = valueDescription;
	}

	public Long getIdMapping() {
		return idMapping;
	}

	// @JsonIgnore se utiliza si no se quiere setear este valor
	public void setId(Long id) {
		this.idMapping = id;
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
