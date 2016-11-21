package com.neu.xunweb.springview;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

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
import com.neu.xunweb.pojo.CDrug;

public class DoctorPdfView extends AbstractPdfView
{
	private String patientname;
	
	private String doctorname;
	
	private Set<CDrug> cdrugs;
	
	public DoctorPdfView(){
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

	public Set<CDrug> getCdrugs() {
		return cdrugs;
	}

	public void setCdrugs(Set<CDrug> cdrugs) {
		this.cdrugs = cdrugs;
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document pdfdoc, PdfWriter pdfwriter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Font font_times_18_normal_black = new Font(Font.TIMES_ROMAN, 18, Font.NORMAL, Color.black);
		Font font_times_24_bold_black = new Font(Font.TIMES_ROMAN, 24, Font.BOLD, Color.black);
		
		Chunk header = new Chunk("Health Appointment", font_times_24_bold_black);
		Paragraph prg0 = new Paragraph();
		prg0.add(header);
		prg0.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph prg1 = new Paragraph("Patient Name: " + patientname, font_times_18_normal_black);
		Paragraph prg2 = new Paragraph("Doctor Name: " + doctorname, font_times_18_normal_black);
		Paragraph prg3 = new Paragraph("Drugs:", font_times_18_normal_black);
		
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(prg0);
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(prg1);
		pdfdoc.add(prg2);
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(prg3);
		
		int totalprice = 0;
		for(CDrug cdrug: cdrugs){
			Paragraph p = new Paragraph("Drug Name: " + cdrug.getName() 
			+ "        Quantity: " + cdrug.getQuantity() + "        Price: " + cdrug.getPrice() + "$\n", font_times_18_normal_black);
			Integer price = Integer.parseInt(cdrug.getPrice());
			Integer quantity = Integer.parseInt(cdrug.getQuantity());
			totalprice = totalprice + price * quantity;
			pdfdoc.add(p);
		}
		
		Paragraph prg4 = new Paragraph("Total price: " + totalprice + "$", font_times_18_normal_black);
		Paragraph prg5 = new Paragraph("Please go to the Pharmacy and pick up the drugs.", font_times_18_normal_black);
		Paragraph prg6 = new Paragraph("Thank you!", font_times_18_normal_black);
		
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String imageurl = path.substring(0, path.length() - 8);
		Image image = Image.getInstance(imageurl + "appointment.jpg");		
		
		pdfdoc.add(new Phrase("\n"));		
		pdfdoc.add(prg4);
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(prg5);
		pdfdoc.add(prg6);
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(new Phrase("\n"));
		pdfdoc.add(image);
	}

}
