package com.example.auto_warehouse.service.impl;

import java.util.List;

import com.example.auto_warehouse.bean.Admin;
import com.example.auto_warehouse.mapper.AdminMapper;
import com.example.auto_warehouse.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminMapper adminMapper;

	@Override
	public List<Admin> findAdmin(String account, String psw) {
		// TODO Auto-generated method stub
		return adminMapper.findAdmin(account, psw);
	}

}
