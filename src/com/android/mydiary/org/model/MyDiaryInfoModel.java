package com.android.mydiary.org.model;

public class MyDiaryInfoModel {
	private String date;
	private String week;
	private String weather;
	private String diaryInfo;
	private float fontSize;
	private int id;
	private boolean checked;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getDiaryInfo() {
		return diaryInfo;
	}
	public void setDiaryInfo(String diaryInfo) {
		this.diaryInfo = diaryInfo;
	}
	public float getFontSize() {
		return fontSize;
	}
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
