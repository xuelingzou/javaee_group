package com.example.auto_warehouse.mapper;

import java.util.List;

import com.example.auto_warehouse.bean.Admin;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AdminMapper {
	List<Admin> findAdmin(String account, String psw);
}
