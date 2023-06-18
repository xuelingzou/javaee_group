package com.zhengyuan.liunao.service;

import java.util.List;
import java.util.Map;

import com.zhengyuan.liunao.entity.Company;
//import com.zhengyuan.liunao.entity.Scores;

public interface CompanyService {
	List<Company> findCompany(String coid, String psw);
	int addCompany(Map<String, String> map);
	List<Company> findAllCompany(Map<String, Object> map);

	List<Company> findAll();

	List<Company> findCompanyByName(String coName, int start, int pagesize);

	int companyCount();


	int deleteByForeach(List<String> coid);

	int deleteCompany(String coid);

	List<Company> getCompanyByNum(String coid);

	int updateCompany(Map<String,String> map);

}
