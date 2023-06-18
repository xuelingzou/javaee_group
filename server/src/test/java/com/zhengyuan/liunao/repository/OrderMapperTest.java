package com.zhengyuan.liunao.repository;

import cn.hutool.core.date.DateTime;
import com.zhengyuan.liunao.entity.Logistics;
import com.zhengyuan.liunao.entity.Order;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderMapperTest extends TestCase {

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void testAddOrder() throws ParseException {
        // 新建order对象
        Order order = new Order("20301155", "Leeer", "13051030228", "beijing", "dtl", "18220222827", "shaanxi", "clothes", 2,0.5);
        // 获取当前日期
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowTime = tFormat.parse(tFormat.format(now));
        // 提交订单
        orderMapper.addOrder(order);
        // 获取刚添加的订单
        List<Order> orders = orderMapper.showAllOrder();
        Order newOrder = orders.get(orders.size()-1);
        // assert断言
        assertEquals("20301155", newOrder.getCeid());
        assertEquals("Leeer", newOrder.getSenderName());
        assertEquals("13051030228", newOrder.getSenderPhone());
        assertEquals("beijing", newOrder.getDeparture());
        assertEquals("dtl", newOrder.getReceiveName());
        assertEquals("18220222827", newOrder.getReceivePhone());
        assertEquals("shaanxi", newOrder.getDestination());
        assertEquals("clothes", newOrder.getCargoType());
        assertEquals(2.0, newOrder.getWeight());
        assertEquals(0.5, newOrder.getVolume());
        assertEquals(nowTime, newOrder.getSubmitTime());
    }

    @Test
    public void testUpdateCoidNState() {
        // 获取刚添加的订单
        List<Order> orders = orderMapper.showAllOrder();
        // 接单
        orderMapper.updateCoidNState(orders.get(orders.size()-1).getOid(), "456789");
        // 获取刚接的订单
        orders = orderMapper.showAllOrder();
        Order lastOrder = orders.get(orders.size()-1);
        // assert断言
        assertEquals("456789", lastOrder.getCoid());
        assertEquals("已接单", lastOrder.getState());
    }

    @Test
    public void testUpdateSendTNState() throws ParseException {
        // 获取刚接的订单
        List<Order> orders = orderMapper.showAllOrder();
        // 获取当前日期
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowTime = tFormat.parse(tFormat.format(now));
        // 发货
        orderMapper.updateSendTNState(orders.get(orders.size()-1).getOid(), nowTime);
        // 获取刚发货的订单
        orders = orderMapper.showAllOrder();
        Order lastOrder = orders.get(orders.size()-1);
        // assert 断言
        assertEquals("已发货", lastOrder.getState());
        assertEquals(nowTime, lastOrder.getSendTime());
    }

    @Test
    public void testUpdateReceiveTNState() throws ParseException {
        // 获取刚接的订单
        List<Order> orders = orderMapper.showAllOrder();
        // 获取当前日期
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowTime = tFormat.parse(tFormat.format(now));
        // 发货
        orderMapper.updateSendTNState(orders.get(orders.size()-1).getOid(), nowTime);
        // 获取刚发货的订单
        orders = orderMapper.showAllOrder();
        Order lastOrder = orders.get(orders.size()-1);
        // assert 断言
        assertEquals("已发货", lastOrder.getState());
        assertEquals(nowTime, lastOrder.getSendTime());
    }

    @Test
    public void testFindOrderByCeid() {
        List<Order> waitOrder = orderMapper.findOrderByCeid("20301155");
        for(int i=0; i<waitOrder.size(); i++){
            assertEquals("20301155", waitOrder.get(i).getCoid());
        }
    }

    @Test
    public void testFindOrderByCoid() {
        List<Order> waitOrder = orderMapper.findOrderByCoid("456789");
        for(int i=0; i<waitOrder.size(); i++){
            assertEquals("456789", waitOrder.get(i).getCoid());
        }
    }

    @Test
    public void testFindOrderWaitReceive() {
        List<Order> waitOrder = orderMapper.findOrderWaitReceive();
        for(int i=0; i<waitOrder.size(); i++){
            assertEquals("待接单", waitOrder.get(i).getState());
        }
    }

    @Test
    public void testShowAllOrder() {
        assertNotNull(orderMapper.showAllOrder());
    }

    @Test
    public void testAddLogistics() {
        Date now = new Date();
        Logistics logistics = new Logistics(51,now,"发往沧州");
        int result = orderMapper.addLogistics(logistics);
        assertEquals(1,result);
    }

    @Test
    public void testShowAllLogisticsByOid() {
        List<Logistics> result = orderMapper.showAllLogisticsByOid(51);
        assertEquals(3,result.size());
        for(int i=0;i< result.size();i++){
            assertEquals(51,result.get(i).getOid());
        }
    }
}