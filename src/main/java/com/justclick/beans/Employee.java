package com.justclick.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * Table employee ada di setiap schema (kecuali public)
 *
 */
@Entity
@Table(name = "employee")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee implements Serializable {
    private static final long serialVersionUID = -4551953276601557391L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private Integer id;

    @Column(name = "employee_name", length = 50, nullable = false)
    private String name;
    
    @Column(name = "employee_nik", length = 25, nullable = true)
    private String nik;
    
    @Column(name = "employee_address", nullable = true)
    private String address;

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
    

    public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}