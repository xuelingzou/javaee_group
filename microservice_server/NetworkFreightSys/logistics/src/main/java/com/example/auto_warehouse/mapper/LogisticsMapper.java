package com.example.auto_warehouse.mapper;


import com.example.auto_warehouse.bean.Logistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogisticsMapper {

    // 承运商添加物流信息
    int addLogistics(Logistics logistics);

    // 根据oid显示其所有物流信息
    List<Logistics> showAllLogisticsByOid(@Param("oid")int oid);

}
