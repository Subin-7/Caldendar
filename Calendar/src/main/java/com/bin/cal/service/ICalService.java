package com.bin.cal.service;

import java.util.List;

import com.bin.cal.dto.CalDto;

public interface ICalService {
	
		//일정 추가하기 :insert문 , 파라미터 id,title,content,mdate 4개의 값을 받는다 
		public boolean insertCal(CalDto dto);
		
		//일정목록 조회하기 :select문, 결과 : List, 파라미터 : id, yyyyMMdd
		public List<CalDto> calBoardList(String id, String yyyyMMdd);
		
		//일정 상세보기 : select문, 결과 : CalDto, 파라미터 : seq
		public CalDto calDetatil(int seq);
		
		//일정 수정하기
		public boolean calUpdate(CalDto dto);
		
		//일정 삭제하기(여러개 / 한개 삭제하기)
		public boolean calMuldel(String[] seq);
		
		//일정의 개수 조회하기 select문, 파라미터 : id, yyyyMMdd
		public int calCount(String id , String yyyyMMdd);
		
		//달력에 존재하는 일정 조회하기 (일일별 최대 3개씩 가져오기)
		public List<CalDto> calViewList(String id, String yyyyMM);

}
