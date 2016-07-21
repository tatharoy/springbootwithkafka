package com.davita.pie.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by taroy on 4/25/16.
 */

@Entity
public class Facility extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	 @Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    private String name;

    private String type;
    
   

    @Embedded
    private Address facilityAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Address getFacilityAddress() {
		return facilityAddress;
	}

	public void setFacilityAddress(Address facilityAddress) {
		this.facilityAddress = facilityAddress;
	}

	@Override
	public String toString() {
		return "Facility [name=" + name + ", type=" + type + ", id=" + getId()+ ", facilityAddress=" + facilityAddress
				+ "]";
	}


    
}
