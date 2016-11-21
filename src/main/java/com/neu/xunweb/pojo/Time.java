package com.neu.xunweb.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="time")
public class Time {
	
	@Id @GeneratedValue
	@Column(name="timeid")
	private int timeid;
	
	@Column(name="status")
	private int status;
	
	@Column(name="date")
	private String date;
	
	@Column(name="nineam")
	private int nineam;
	
	@Column(name="tenam")
	private int tenam;
	
	@Column(name="elevenam")
	private int elevenam;
	
	@Column(name="onepm")
	private int onepm;
	
	@Column(name="twopm")
	private int twopm;
	
	@Column(name="threepm")
	private int threepm;
	
	@Column(name="fourpm")
	private int fourpm;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctoraccount")
    private DoctorAccount doctoraccount;
	
	public Time(){
		status = 1;
	}

	public int getTimeid() {
		return timeid;
	}

	public void setTimeid(int timeid) {
		this.timeid = timeid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNineam() {
		return nineam;
	}

	public void setNineam(int nineam) {
		this.nineam = nineam;
	}

	public int getTenam() {
		return tenam;
	}

	public void setTenam(int tenam) {
		this.tenam = tenam;
	}

	public int getElevenam() {
		return elevenam;
	}

	public void setElevenam(int elevenam) {
		this.elevenam = elevenam;
	}

	public int getOnepm() {
		return onepm;
	}

	public void setOnepm(int onepm) {
		this.onepm = onepm;
	}

	public int getTwopm() {
		return twopm;
	}

	public void setTwopm(int twopm) {
		this.twopm = twopm;
	}

	public int getThreepm() {
		return threepm;
	}

	public void setThreepm(int threepm) {
		this.threepm = threepm;
	}

	public int getFourpm() {
		return fourpm;
	}

	public void setFourpm(int fourpm) {
		this.fourpm = fourpm;
	}

	public DoctorAccount getDoctoraccount() {
		return doctoraccount;
	}

	public void setDoctoraccount(DoctorAccount doctoraccount) {
		this.doctoraccount = doctoraccount;
	}

}
