package com.example.auto_warehouse.mapper;

import java.util.List;
import java.util.Map;

import com.example.auto_warehouse.bean.Client;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientMapper {
	List<Client> findClient(String ceid, String psw);
	int addClient(Map<String,String> map);

	List<Client> findAllClient(Map<String, Object> map);

	List<Client> findClientByName(String ceName, int start, int pagesize);

	int ClientCount();
	
	List<Client> findClientByNum(String ceid);

	int updateClient(Map<String, String> map);
	int deleteByForeach(List<String> ceid);

	int deleteClient(String ceid);
}
