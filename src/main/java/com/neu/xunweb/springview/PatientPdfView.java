package com.neu.xunweb.springview;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

public class PatientPdfView extends AbstractPdfView
{
	private String patientname;
	
	private String doctorname;
	
	private Long reservationnumber;
	
	private String time;
	
	private String date;
	
	private String address;
	
	public PatientPdfView(){
	}
	
	public String getPatientname() {
		return patientname;
	}

	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	public String getDoctorname() {
		return doctorname;
	}

	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}

	public Long getReservationnumber() {
		return reservationnumber;
	}

	public void setReservationnumber(Long reservationnumber) {
		this.reservationnumber = reservationnumber;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document pdfdoc, PdfWriter pdfwriter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Font font_times_18_normal_black = new Font(Font.TIMES_ROMAN, 18, Font.NORMAL, Color.black);
		Font font_times_18_normal_red = new Font(Font.TIMES_ROMAN, 18, Font.NORMAL, Color.red);
		Font font_times_24_bold_black = new Font(Font.TIMES_ROMAN, 24, Font.BOLD, Color.black);
		
		String reservation = String.valueOf(reservationnumber);
		
		Chunk header = new Chunk("Health Appointment", font_times_24_bold_black);
		Paragraph prg0 = new Paragraph();
		prg0.add(header);
		prg0.setAlignment(Element.ALIGN_CENTER);
		Chunk c2 = new Chunk("Hello " + patientname + ",", font_times_18_normal_black);
		Chunk c3 = new Chunk(doctorname, font_times_18_normal_red);
		Chunk c4 = new Chunk(time + ":00", font_times_18_normal_red);
		Chunk c5 = new Chunk(date, font_times_18_normal_red);
		Chunk c6 = new Chunk(address, font_times_18_normal_red);
		Chunk c7 = new Chunk(reservation, font_times_18_normal_red);
		
		Paragraph prg1 = new Paragraph("You have made the appointment successfully!", font_times_18_normal_black);
		Paragraph prg2 = new Paragraph("Please print this page and go to see doctor ", font_times_18_normal_black);
		prg2.add(c3);
		prg2.add(" at ");
		prg2.add(c4);
		prg2.add(" on ");
		prg2.add(c5);
		prg2.add(".");
		Paragraph prg3 = new Paragraph("The place is in ", font_times_18_normal_black);
		prg3.add(c6);
		prg3.add(".");
		Paragraph prg4 = new Paragraph("Your reservation number is ", font_times_18_normal_black);
		prg4.add(c7);
		prg4.add(".");
		Paragraph prg5 = new Paragraph("Thanks,", font_times_18_normal_black);
		Paragraph prg6 = new Paragraph("Health Appointment", font_times_18_normal_black);
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String imageurl = path.substring(0, path.length() - 8);
		Image image = Image.getInstance(imageurl + "appointment.jpg");
		
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(prg0);
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(c2);
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(prg1);
		pdfdoc.add(prg2);
		pdfdoc.add(prg3);
		pdfdoc.add(prg4);
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(prg5);
		pdfdoc.add(prg6);
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(image);
	}

}
