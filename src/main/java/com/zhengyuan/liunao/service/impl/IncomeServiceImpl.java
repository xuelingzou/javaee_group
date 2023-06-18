package com.zhengyuan.liunao.service.impl;

import java.util.List;
import java.util.Map;

import com.zhengyuan.liunao.entity.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhengyuan.liunao.repository.IncomeMapper;
import com.zhengyuan.liunao.service.IncomeService;

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	IncomeMapper incomeMapper;
	
	
	@Override
	public int updateIncome(int yearMonth, double money, String companyID) {
		// TODO Auto-generated method stub
		return incomeMapper.updateIncome(yearMonth,money,companyID);
	}


	@Override
	public int insertIncome(Income income) {
		// TODO Auto-generated method stub
		return incomeMapper.insertIncome(income);
	}


	@Override
	public List<Income> findByYearMonth(String companyID, int yearMonth) {
		// TODO Auto-generated method stub
		return incomeMapper.findByYearMonth(companyID,yearMonth);
	}
	@Override
	public List<Income> selectMonthIncome(String companyID, int year) {
		// TODO Auto-generated method stub
		return incomeMapper.selectMonthIncome(companyID,year);
	}

}
