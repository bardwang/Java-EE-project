package com.neu.xunweb.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="organization")
public class Organization {
	
	@Id @GeneratedValue
	@Column(name="organizationid")
	private int organizationid;
	
	@Column(name="name",unique=true)
	private String name;
	
	@Column(name="type")
	private String type;
	
	@Column(name="city")
	private String city;
	
	@Transient
	private int available = 0;
	
	@Transient
	private int total = 0;
	
	@Column(name="removed")
	private int removed = 0;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="organization", cascade = CascadeType.ALL)
	private Set<Drug> drugs;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="organization")
	private Set<DoctorAccount> doctoraccounts;
	
	public Organization(){
		drugs = new HashSet<Drug>();
		doctoraccounts = new HashSet<DoctorAccount>();
	}

	public int getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(int organizationid) {
		this.organizationid = organizationid;
	}

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<Drug> getDrugs() {
		return drugs;
	}

	public void setDrugs(Set<Drug> drugs) {
		this.drugs = drugs;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRemoved() {
		return removed;
	}

	public void setRemoved(int removed) {
		this.removed = removed;
	}

	public Set<DoctorAccount> getDoctoraccounts() {
		return doctoraccounts;
	}

	public void setDoctoraccounts(Set<DoctorAccount> doctoraccounts) {
		this.doctoraccounts = doctoraccounts;
	}
	
}
