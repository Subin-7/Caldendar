package com.bin.cal.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.text.SimpleDateFormat;

public class Util {
	
	private String toDates;
	
	public String getToDates() {
		return toDates;
	}

	public void setToDates(String mdate){
		
		//문자열 ---> date타입으로 변환-->  문자열을 데이트패턴으로 수정 --> 데이트타입으로 변환
		
		//날짜형식 : yyyy-MM-dd hh:mm:ss
		String m = mdate.substring(0,4)+"-"
				+mdate.substring(4,6)+"-"
				+mdate.substring(6,8)+" "
				+mdate.substring(8,10)+":"
				+mdate.substring(10)+":00";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");
		Timestamp tm = Timestamp.valueOf(m); //문자열을 Date타입으로 변환
		
		this.toDates= sdf.format(tm);
	}
	
	//토요일과 일요일을 확인해서 "blue"또는 "red" 문자열을 반환하는 메소드 
	public static String fontColor(int dayOfWeek, int i){
		String color="";
		if((dayOfWeek-1+i)%7==0){//토요일인 경우
			color="blue";
		}else if((dayOfWeek-1+i)%7==1){	//일요일인 경우
			color="red";
		}else{
			color="black";
		}
		return color;
	}
	
	
	//1자리 문자열을 2자리 문자열로 변환하는 메서드 <--삼항연산자의 이해 
	public static String isTwo(String msg) {
		
		return msg.length()<2?"0"+msg:msg;
	}
	
}
