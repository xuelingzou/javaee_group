package com.example.auto_warehouse.service.impl;

import com.example.auto_warehouse.bean.Logistics;
import com.example.auto_warehouse.mapper.LogisticsMapper;
import com.example.auto_warehouse.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsServiceImpl  implements LogisticsService {
    @Autowired
    private LogisticsMapper logisticsMapper;

    // 承运商添加物流信息
    @Override
    public int addLogistics(Logistics logistics){
        // 对数据库的操作
        return logisticsMapper.addLogistics(logistics);
    }

    // 根据oid显示其所有物流信息
    @Override
    public List<Logistics> showAllLogisticsByOid(int oid){
        return logisticsMapper.showAllLogisticsByOid(oid);
    }
}