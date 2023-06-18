package com.zhengyuan.liunao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zhengyuan.liunao.entity.Admin;

@Mapper
public interface AdminMapper {
	List<Admin> findAdmin(String account, String psw);
}
