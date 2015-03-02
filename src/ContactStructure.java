package com.example.oducs_mobile;

public class ContactStructure {
	
	private String con_name;
	private String con_office;
	private String con_email;
	private String cont_phone;
	
	public String getCon_name() {
		return con_name;
	}
	
	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

	public String getCon_office() {
		return con_office;
	}

	public void setCon_office(String con_office) {
		this.con_office = con_office;
	}

	public String getCon_email() {
		return con_email;
	}

	public void setCon_email(String con_email) {
		this.con_email = con_email;
	}

	public String getCont_phone() {
		return cont_phone;
	}

	public void setCont_phone(String cont_phone) {
		this.cont_phone = cont_phone;
	}
	
	public String toString(){
		return("ContactList [con_name=" + con_name + ", con_office=" + con_office + ", con_email="+ con_email + ", cont_phone=" + cont_phone + "]");
	}
	

}
