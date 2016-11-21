package com.neu.xunweb.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="doctoraccount")
@PrimaryKeyJoinColumn(name="accountid")
public class DoctorAccount extends Account{
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="organization")
    private Organization organization;
	
	@Transient
	private String organizationname;
	
	@Transient
	private String organizationtype;
	
	@Column(name="name")
	private String name;
	
	@Column(name="telephone")
	private String telephone;

	@Column(name="concentration")
	private String concentration;
	
	@Column(name="yearsofexperience")
    private String yearsofexperience;
	
	@Column(name="background")
    private String background;
	
	@Column(name="status")
    private int status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="doctoraccount")
	private Set<Time> times;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="doctoraccount")
	private Set<Appointment> appointments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="doctoraccount", cascade=CascadeType.ALL)
	private Set<CDrug> cdrugs;
	
	public DoctorAccount(){
		times = new HashSet<Time>();
		appointments = new HashSet<Appointment>();
		cdrugs = new HashSet<CDrug>();
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

	public String getConcentration() {
		return concentration;
	}

	public void setConcentration(String concentration) {
		this.concentration = concentration;
	}

	public String getYearsofexperience() {
		return yearsofexperience;
	}

	public void setYearsofexperience(String yearsofexperience) {
		this.yearsofexperience = yearsofexperience;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Time> getTimes() {
		return times;
	}

	public void setTimes(Set<Time> times) {
		this.times = times;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Set<CDrug> getCdrugs() {
		return cdrugs;
	}

	public void setCdrugs(Set<CDrug> cdrugs) {
		this.cdrugs = cdrugs;
	}
}
