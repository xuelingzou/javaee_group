package com.zhengyuan.liunao.service.impl;

import java.util.List;
import java.util.Map;

import com.zhengyuan.liunao.entity.Company;
import com.zhengyuan.liunao.repository.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhengyuan.liunao.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyMapper companyMapper;
	
	@Override
	public List<Company> findCompany(String coid, String psw) {
		// TODO Auto-generated method stub
		return companyMapper.findCompany(coid, psw);
	}

	@Override
	public int addCompany(Map<String,String> map) {
		return companyMapper.addCompany(map);
	}
	
	
	@Override
	public List<Company> findAllCompany(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return companyMapper.findAllCompany(map);
	}

	@Override
	public List<Company> findCompanyByName(String coName, int start, int pagesize) {
		// TODO Auto-generated method stub
		return companyMapper.findCompanyByName(coName, start, pagesize);
	}

	@Override
	public int companyCount() {
		// TODO Auto-generated method stub
		return companyMapper.companyCount();
	}


	@Override
	public int deleteByForeach(List<String> coid) {
		// TODO Auto-generated method stub
		return companyMapper.deleteByForeach(coid);
	}

	@Override
	public int deleteCompany(String coid) {
		// TODO Auto-generated method stub
		return companyMapper.deleteCompany(coid);
	}

	@Override
	public List<Company> getCompanyByNum(String coid) {
		// TODO Auto-generated method stub
		return companyMapper.getCompanyByNum(coid);
	}

	@Override
	public List<Company> findAll() {
		// TODO Auto-generated method stub
		return companyMapper.findAll();
	}

	@Override
	public int updateCompany(Map<String,String> map) {
		// TODO Auto-generated method stub
		return companyMapper.updateCompany(map);
	}


}
