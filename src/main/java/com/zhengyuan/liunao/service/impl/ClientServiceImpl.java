package com.zhengyuan.liunao.service.impl;

import java.util.List;
import java.util.Map;

import com.zhengyuan.liunao.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhengyuan.liunao.entity.Client;
import com.zhengyuan.liunao.repository.ClientMapper;
import com.zhengyuan.liunao.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	
	@Autowired
	ClientMapper clientMapper;
	
	@Override
	public List<Client> findClient(String ceid, String psw) {
		// TODO Auto-generated method stub
		return clientMapper.findClient(ceid, psw);
	}

	@Override
	public int addClient(Map<String,String> map) {
		// TODO Auto-generated method stub
		return clientMapper.addClient(map);
	}
	
	@Override
	public List<Client> findAllClient(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return clientMapper.findAllClient(map);
	}

	@Override
	public List<Client> findClientByName(String ceName, int start, int pagesize) {
		// TODO Auto-generated method stub
		return clientMapper.findClientByName(ceName, start, pagesize);
	}

	@Override
	public int ClientCount() {
		// TODO Auto-generated method stub
		return clientMapper.ClientCount();
	}

	@Override
	public List<Client> findClientByNum(String ceid) {
		// TODO Auto-generated method stub
		return clientMapper.findClientByNum(ceid);
	}

	@Override
	public int updateClient(Map<String,String> map) {
		// TODO Auto-generated method stub
		return clientMapper.updateClient(map);
	}
	@Override
	public int deleteByForeach(List<String> ceid) {
		// TODO Auto-generated method stub
		return clientMapper.deleteByForeach(ceid);
	}
	@Override
	public int deleteClient(String ceid) {
		// TODO Auto-generated method stub
		return clientMapper.deleteClient(ceid);
	}

}
