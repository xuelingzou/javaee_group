package com.zhengyuan.liunao.service;

import java.util.List;
import java.util.Map;

import com.zhengyuan.liunao.entity.Income;

public interface IncomeService {
	int updateIncome(int yearMonth, double money, String companyID);
	int insertIncome(Income income);
	List<Income> findByYearMonth(String companyID, int yearMonth);
	List<Income> selectMonthIncome(String companyID, int year);
}
