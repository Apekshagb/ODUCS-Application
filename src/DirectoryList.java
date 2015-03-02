package com.example.oducs_mobile;

public class DirectoryList {

	private String name;
	private String type;
	private String position;
	private String office;
	private String email;
	private String phone;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String toString() {
		return "DirectoryList [name=" + name + ", positon=" + position
				+ ", office=" + office + ", email=" + email + ", phone="
				+ phone + "]";
	}
}
