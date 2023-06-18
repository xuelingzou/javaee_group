package com.example.auto_warehouse.service.impl;

import java.util.List;

import com.example.auto_warehouse.bean.Income;
import com.example.auto_warehouse.mapper.IncomeMapper;
import com.example.auto_warehouse.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
