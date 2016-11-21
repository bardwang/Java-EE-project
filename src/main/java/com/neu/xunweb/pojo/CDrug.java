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
@Table(name="cdrug")
public class CDrug {

	@Id @GeneratedValue
	@Column(name="drugid")
	private int drugid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="pharma")
	private String pharma;
	
	@Column(name="price")
	private String price;
	
	@Column(name="quantity")
	private String quantity;
	
	@Column(name="removed")
	private int removed = 0;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctoraccount")
    private DoctorAccount doctoraccount;
	
	public CDrug(){}

	public int getDrugid() {
		return drugid;
	}

	public void setDrugid(int drugid) {
		this.drugid = drugid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPharma() {
		return pharma;
	}

	public void setPharma(String pharma) {
		this.pharma = pharma;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public int getRemoved() {
		return removed;
	}

	public void setRemoved(int removed) {
		this.removed = removed;
	}

	public DoctorAccount getDoctoraccount() {
		return doctoraccount;
	}

	public void setDoctoraccount(DoctorAccount doctoraccount) {
		this.doctoraccount = doctoraccount;
	}

}
