package com.example.auto_warehouse.service.impl;

import com.example.auto_warehouse.bean.Logistics;
import com.example.auto_warehouse.bean.Order;
import com.example.auto_warehouse.mapper.OrderMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl {
    @Autowired
    private OrderMapper orderMapper;

    // 客户提交订单
    void submitOrder(Order order){
        // 对数据库的操作
        orderMapper.addOrder(order);
    }

    // 货运公司接单
    void receiveOrder(int oid, String coid){
        // 更改数据库中该条order的状态，设定关联货运公司
        orderMapper.updateCoidNState(oid, coid);
    }

    // 货运公司发货
    void sendCargo(int oid) throws ParseException {
        // 获取当前时间
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sendTime = tFormat.parse(tFormat.format(now));
        // 修改数据库对应数据
        orderMapper.updateSendTNState(oid, sendTime);
    }

    // 货运公司送达
    void receiveCargo(int oid) throws ParseException {
        // 获取当前时间
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date receiveTime = tFormat.parse(tFormat.format(now));
        // 修改数据库对应数据
        orderMapper.updateSendTNState(oid, receiveTime);
    }

    // 承运商添加物流信息
    int addLogistics(Logistics logistics){
        // 对数据库的操作
        return orderMapper.addLogistics(logistics);
    }

    // 根据oid显示其所有物流信息
    List<Logistics> showAllLogisticsByOid(int oid){
        return orderMapper.showAllLogisticsByOid(oid);
    }
}