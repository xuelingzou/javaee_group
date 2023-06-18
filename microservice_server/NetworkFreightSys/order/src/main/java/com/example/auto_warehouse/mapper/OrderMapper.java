package com.example.auto_warehouse.mapper;


import com.example.auto_warehouse.bean.Logistics;
import com.example.auto_warehouse.bean.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    // 添加订单
    int addOrder(Order order);

    // 货运公司接单————更新order表的coid、state
    int updateCoidNState(@Param("oid") int oid, @Param("coid")String coid);

    // 货运公司发货————更新order表的sendTime、state
    int updateSendTNState(@Param("oid")int oid, @Param("sendTime")Date sendTime);

    // 货运公司送达————更新order表的state、receiveTime
    int updateReceiveTNState(@Param("oid")int oid, @Param("receiveTime")Date receiveTime);

    // 客户界面————展示自己的全部订单
    List<Order> findOrderByCeid(@Param("ceid")String ceid);

    // 货运公司界面————展示自己的全部订单
    List<Order> findOrderByCoid(@Param("coid")String coid);

    // 货运公司界面————展示全部待接单
    List<Order> findOrderWaitReceive();

    // 管理员界面————展示全部订单
    List<Order> showAllOrder();

    // 通过订单id查找订单
    Order findOrderByOid(@Param("oid")int oid);

    // 检索不同货物的承运人账单
    List<Order> findOrderByCargotype(@Param("cargoType") String cargoType,@Param("coid") String coid);

    // 承运商添加物流信息
    int addLogistics(Logistics logistics);

    // 根据oid显示其所有物流信息
    List<Logistics> showAllLogisticsByOid(@Param("oid")int oid);

}
