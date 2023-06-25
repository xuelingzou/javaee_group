package com.example.auto_warehouse.mapper;

import java.util.List;

import com.example.auto_warehouse.bean.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface AdminMapper {
	List<Admin> findAdmin(@Param("account")String account, @Param("psw")String psw);
}
