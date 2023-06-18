package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.Income;

import java.util.List;
import java.util.Map;


public interface IncomeService {
	int updateIncome(int yearMonth, double money, String companyID);
	int insertIncome(Income income);
	List<Income> findByYearMonth(String companyID, int yearMonth);
	List<Income> selectMonthIncome(String companyID, int year);
}
