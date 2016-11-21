package com.neu.xunweb.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="pharmacistaccount")
@PrimaryKeyJoinColumn(name="accountid")
public class PharmacistAccount extends Account{
	
	@Column(name="name")
	private String name;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="status")
	private int status;
	
	@Transient
	private String organizationname;
	
	@Transient
	private String organizationtype;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="organization")
    private Organization organization;
	
	public PharmacistAccount(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getOrganizationtype() {
		return organizationtype;
	}

	public void setOrganizationtype(String organizationtype) {
		this.organizationtype = organizationtype;
	}

}
