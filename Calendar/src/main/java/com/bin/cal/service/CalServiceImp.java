package com.bin.cal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bin.cal.dao.ICalDao;
import com.bin.cal.dto.CalDto;

@Service
public class CalServiceImp implements ICalService{
	
	@Autowired
	private ICalDao calDao;

	@Override
	public boolean insertCal(CalDto dto) {
		return calDao.insertCal(dto);
	}

	@Override
	public List<CalDto> calBoardList(String id, String yyyyMMdd) {
		return calDao.calBoardList(id, yyyyMMdd);
	}

	@Override
	public CalDto calDetatil(int seq) {
		return calDao.calDetatil(seq);
	}

	@Override
	public boolean calUpdate(CalDto dto) {
		return calDao.calUpdate(dto);
	}

	@Override
	public boolean calMuldel(String[] seq) {
		return calDao.calMuldel(seq);
	}

	@Override
	public int calCount(String id, String yyyyMMdd) {
		return calDao.calCount(id, yyyyMMdd);
	}

	@Override
	public List<CalDto> calViewList(String id, String yyyyMM) {
		return calDao.calViewList(id, yyyyMM);
	}
	

}
