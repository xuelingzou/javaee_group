package com.example.auto_warehouse.mapper;

import java.util.List;

import com.example.auto_warehouse.bean.Income;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface IncomeMapper {
	int updateIncome(@Param("yearMonth") int yearMonth, @Param("money") double money, @Param("companyID") String companyID);
	int insertIncome(Income income);
	List<Income> findByYearMonth(@Param("companyID")String companyID, @Param("yearMonth")int yearMonth);
	List<Income> selectMonthIncome(@Param("companyID")String companyID, @Param("year")int year);

}
