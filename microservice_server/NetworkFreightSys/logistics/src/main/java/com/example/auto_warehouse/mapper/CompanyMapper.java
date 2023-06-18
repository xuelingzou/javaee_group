package com.example.auto_warehouse.mapper;


import com.example.auto_warehouse.bean.Company;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CompanyMapper {
    int addCompany(Map<String, String> map);
    List<Company> findCompany(String coid, String psw);
    List<Company> findAllCompany(Map<String, Object> map);

    List<Company> findAll();

    List<Company> findCompanyByName(String coName, int start, int pagesize);

    int companyCount();

    int deleteByForeach(List<String> coid);

    int deleteCompany(String coid);

    List<Company> getCompanyByNum(String coid);
    int updateCompany(Map<String,String> map);
}
