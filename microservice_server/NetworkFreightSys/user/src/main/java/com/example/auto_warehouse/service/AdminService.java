package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.Admin;

import java.util.List;


public interface AdminService {
	List<Admin> findAdmin(String account, String psw);
}
