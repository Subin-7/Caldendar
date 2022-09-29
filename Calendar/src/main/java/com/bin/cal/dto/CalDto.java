package com.bin.cal.dto;

import java.util.Date;

public class CalDto {
	
	private int seq;//일정 번호 (pk)
	private String id;//사용자 아이디
	private String title;//일정제목
	private String content;//일정내용
	private String mdate;//일정날짜
	private Date regdate;//일정 작성일
	
	//날짜 정보 파라미터를 받을 용도 
	private String year;
	private String month;
	private String date;
	private String hour;
	private String min;
	
	
	public CalDto() {
		super();
	}


	public CalDto(int seq, String id, String title, String content, String mdate, Date regdate) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.mdate = mdate;
		this.regdate = regdate;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getMdate() {
		return mdate;
	}


	public void setMdate(String mdate) {
		this.mdate = mdate;
	}


	public Date getRegdate() {
		return regdate;
	}


	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	
	

	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getHour() {
		return hour;
	}


	public void setHour(String hour) {
		this.hour = hour;
	}


	public String getMin() {
		return min;
	}


	public void setMin(String min) {
		this.min = min;
	}


	@Override
	public String toString() {
		return "CalDto [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", mdate=" + mdate
				+ ", regdate=" + regdate + "]";
	}
	
	
	
	

}
