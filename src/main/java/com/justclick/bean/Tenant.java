package com.justclick.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Table tenants diletakkan di schema public
 *
 */
@Entity
@Table(name = "tenants")
public class Tenant {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tenant_id")
    private Integer id;
	
	@Column(name="tenant_name", length=50)
	private String name;
	
	@Column(name="tenant_address", length=100)
	private String address;
	
	@Column(name="tenant_schema", length=100)
	private String schema;
	
	public Tenant() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	
}
