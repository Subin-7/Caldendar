package com.bin.cal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bin.cal.dto.CalDto;

@Repository
public class CalDalImp implements ICalDao{
	
	private static final String NS = "com.bin.cal.dao.CalDalImp.";
//	private String namespace ="com.min.eud.";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//일정 추가하기 :insert문 , 파라미터 id,title,content,mdate 4개의 값을 받는다 
	public boolean insertCal(CalDto dto) {
		int count = sqlSession.insert(NS+"insertCal",dto);
		
		return count>0?true:false;
	}
	
	//일정목록 조회하기 :select문, 결과 : List, 파라미터 : id, yyyyMMdd
	public List<CalDto> calBoardList(String id, String yyyyMMdd){
		//mapper.xml에 전달할 파라미터가 2개 이상이면 Map에 담아서 전달한다 
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("yyyyMMdd", yyyyMMdd);
		List<CalDto>list = sqlSession.selectList(NS+"calBoardList",map);
		return list;
	}
	
	//일정 상세보기 : select문, 결과 : CalDto, 파라미터 : seq
	public CalDto calDetatil(int seq) {
		CalDto dto = sqlSession.selectOne(NS+"calDetatil", seq);
		return dto;
	}
	
	//일정 수정하기
	public boolean calUpdate(CalDto dto) {
		int count = sqlSession.update(NS+"calUpdate",dto);
		return count>0?true:false;
	}
	
	//일정 삭제하기(여러개 / 한개 삭제하기)
	public boolean calMuldel(String[] seq) {
		Map<String, String[]>map = new HashMap<>();
		map.put("seqs", seq);
		int count = sqlSession.delete(NS+"calMuldel",map);
		return count>0?true:false;
	}
	
	//일정의 개수 조회하기 select문, 파라미터 : id, yyyyMMdd
	public int calCount(String id , String yyyyMMdd) {
		Map<String, String> map = new HashMap<>();
		map.put("id",id);
		map.put("yyyyMMdd", yyyyMMdd);
		int count = sqlSession.selectOne(NS+"calCount", map);
		return count;
		
	}
	
	//달력에 존재하는 일정 조회하기 (일일별 최대 3개씩 가져오기)
	public List<CalDto> calViewList(String id, String yyyyMM){
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("yyyyMM", yyyyMM);
		return sqlSession.selectList(NS+"calViewList", map);
	}
	
	
}
