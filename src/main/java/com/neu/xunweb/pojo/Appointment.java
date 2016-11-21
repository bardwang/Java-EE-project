package com.neu.xunweb.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name="generator", strategy="foreign",
	parameters = @Parameter(name="property", value="patientaccount"))
	@Column(name="appointmentid")
	private int appointmentid;
	
	@Column(name = "reservationnumber", unique = true)
	private Long reservationnumber;

	@Column(name="date")
	private String date;
	
	@Column(name="time")
	private String time;
	
	@Column(name="patientname")
	private String patientname;
	
	@Transient
	private int patientid;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctoraccount")
	private DoctorAccount doctoraccount;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private PatientAccount patientaccount;

	public int getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(int appointmentid) {
		this.appointmentid = appointmentid;
	}

	public Long getReservationnumber() {
		return reservationnumber;
	}

	public void setReservationnumber(Long reservationnumber) {
		this.reservationnumber = reservationnumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPatientname() {
		return patientname;
	}

	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	public int getPatientid() {
		return patientid;
	}

	public void setPatientid(int patientid) {
		this.patientid = patientid;
	}

	public DoctorAccount getDoctoraccount() {
		return doctoraccount;
	}

	public void setDoctoraccount(DoctorAccount doctoraccount) {
		this.doctoraccount = doctoraccount;
	}

	public PatientAccount getPatientaccount() {
		return patientaccount;
	}

	public void setPatientaccount(PatientAccount patientaccount) {
		this.patientaccount = patientaccount;
	}

}
