package com.example.oducs_mobile;

public class EventStructure {

	private String eType;
	private String time;
	private String duration;
	private String day;
	private String note;
	private String reminderAlarm;
	
	
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

	public String geteType() {
		return eType;
	}

	public void seteType(String eType) {
		this.eType = eType;
	}
	
	public String getReminderAlarm() {
		return reminderAlarm;
	}

	public void setReminderAlarm(String reminderAlarm) {
		this.reminderAlarm = reminderAlarm;
	}
	
	public String toString(){
		return "EventList [type=" + eType + ", time=" + time+ ", duration=" + duration + ", day=" + day + ", note="+ note + ",alarm="+reminderAlarm+"]";
	}

	
}
