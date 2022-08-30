package com.justclick.beans;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@NamedStoredProcedureQuery(name = "jc.schemacreate",
procedureName = "p_create_new_schema",
parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name="tenantName", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="tenantAddress", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="tenantSchema", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="userName", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="userPassword", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="userEmail", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="userEnabled", type=Boolean.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name="outMessage", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name="outSuccess", type=Boolean.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name="outIsCreateUserSuccess", type=Boolean.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name="outIsCreateTenantSuccess", type=Boolean.class)
})
@Table(name = "schema_history")

public class SchemaCreatedLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="sh_schema", length=100, nullable = false)
	private String schemaName;
	
	@Column(name = "sh_desc", length=255, nullable = false)
	private String description;
	
	@Column(name="sh_created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name="sh_status", length=100, nullable = false)
	private String status;
	
	public SchemaCreatedLog() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
