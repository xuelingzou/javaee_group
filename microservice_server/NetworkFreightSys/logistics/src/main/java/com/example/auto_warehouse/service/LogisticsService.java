package com.example.auto_warehouse.service;

import com.example.auto_warehouse.bean.Logistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface LogisticsService {
	// 承运商添加物流信息
	int addLogistics(Logistics logistics);

	// 根据oid显示其所有物流信息
	List<Logistics> showAllLogisticsByOid(@Param("oid")int oid);
}
