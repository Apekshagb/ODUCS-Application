package com.example.oducs_mobile;

public class CourseStructure {

	private String crn;
	private String course_title;
	private String credit_hours;
	private String days;
	private String time;
	private String class_location;
	private String instructor;
	private String ta;
	private String office_hours;



	public String getCrn() {
		return crn;
	}


	public void setCrn(String crn) {
		this.crn = crn;
	}


	public String getCourse_title() {
		return course_title;
	}


	public void setCourse_title(String course_title) {
		this.course_title = course_title;
	}


	public String getCredit_hours() {
		return credit_hours;
	}


	public void setCredit_hours(String credit_hours) {
		this.credit_hours = credit_hours;
	}


	public String getDays() {
		return days;
	}


	public void setDays(String days) {
		this.days = days;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getClass_location() {
		return class_location;
	}


	public void setClass_location(String class_location) {
		this.class_location = class_location;
	}


	public String getInstructor() {
		return instructor;
	}


	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}


	public String getTa() {
		return ta;
	}


	public void setTa(String ta) {
		this.ta = ta;
	}


	public String getOffice_hours() {
		return office_hours;
	}


	public void setOffice_hours(String office_hours) {
		this.office_hours = office_hours;
	}
	
	public String toString() {
		return "CourseList [crn=" + crn + ", course_title=" + course_title + ", credit_hours="+ credit_hours + ", days=" + days + ", time="
				+ time + "class_locations="+ class_location+",instructor="+ instructor +",ta="+ ta +",office_hours="+ office_hours+"]";
	}
}
