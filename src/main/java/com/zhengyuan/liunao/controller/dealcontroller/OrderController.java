package com.zhengyuan.liunao.controller.dealcontroller;


import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.zhengyuan.liunao.entity.Income;
import com.zhengyuan.liunao.entity.Logistics;
import com.zhengyuan.liunao.entity.Order;
import com.zhengyuan.liunao.repository.OrderMapper;
import com.zhengyuan.liunao.service.IncomeService;
import com.zhengyuan.liunao.tools.JsonResult;
import com.zhengyuan.liunao.tools.Layui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/Sys")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IncomeService incomeService;

    // 客户提交订单
    @ResponseBody //加这个注解，则直接返回数据，而不是模板路径
    @PostMapping("/addOrder")
    public String submitOrder(@RequestBody Order order, HttpSession httpSession) throws ParseException {
//        String senderName = map.get("senderName");
//        String senderPhone = map.get("senderPhone");
//        String departure = map.get("departure");
//        String receiveName = map.get("receiveName");
//        String receivePhone = map.get("receivePhone");
//        String destination = map.get("destination");
//        String cargoType = map.get("cargoType");
//        double weight = Double.parseDouble(map.get("weight"));
//        double volume = Double.parseDouble(map.get("volume"));
//        // 获取当前客户的ceid
//        String ceid = map.get("ceid");
//        // 新建order对象
//        Order order = new Order(ceid, senderName, senderPhone, departure, receiveName, receivePhone, destination, cargoType, weight, volume);
        // 对数据库的操作
        String ceid = (String) httpSession.getAttribute( "account");
        order.setCeid(ceid); //客户id
        order.setCost(order.getWeight(),order.getVolume());
        if(orderMapper.addOrder(order)>0){
            return "提交订单成功";
        }else{
            return "提交订单失败";
        }

    }

    // 货运公司接单
    @ResponseBody
    @PostMapping("/receiveOrder")
    public String receiveOrder(@RequestParam("oid") int oid, HttpSession httpSession){
        // 获取当前货运公司的coid
        String coid = (String) httpSession.getAttribute( "account");
        // 对数据库的操作：更改数据库中该条order的状态，设定关联货运公司
        orderMapper.updateCoidNState(oid, coid);

        // 更新income表
        double cost = orderMapper.findOrderByOid(oid).getCost();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int yearMonth = year*100+month+1;
        List<Income> list = incomeService.findByYearMonth(coid,yearMonth);
        if(list.size()==0){
            Income income = new Income(coid,yearMonth,cost);
            incomeService.insertIncome(income);
        }else{
            incomeService.updateIncome(yearMonth,cost,coid);
        }
        return "接单成功";
    }

    // 货运公司发货
    @ResponseBody //加这个注解，则直接返回数据，而不是模板路径
    @PostMapping("/sendCargo")
    public String sendCargo(@RequestParam("oid") int oid) throws ParseException {
        // 获取当前时间
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sendTime = tFormat.parse(tFormat.format(now));
        // 修改数据库对应数据
        orderMapper.updateSendTNState(oid, sendTime);
        return "发货成功";
    }

    // 货运公司送达
    @ResponseBody
    @PostMapping("/receiveCargo")
    public String receiveCargo(@RequestParam("oid") int oid) throws ParseException {
        // 获取当前时间
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date receiveTime = tFormat.parse(tFormat.format(now));
        // 修改数据库对应数据
        orderMapper.updateReceiveTNState(oid, receiveTime);
        return "货物已送达";
    }

    // 客户界面————展示自己的全部订单
    @RequestMapping("/findOrderByCeid")
    @ResponseBody
    public String findOrderByCeid(HttpSession httpSession){
        String ceid = (String) httpSession.getAttribute( "account");
        List<Order> orders = orderMapper.findOrderByCeid(ceid);
        List<Map<String, String>> list = new ArrayList<>();
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
        for(Order order:orders){
            Map<String,String> map = new HashMap<>();
            map.put("oid",String.valueOf(order.getOid()));
            map.put("coid",order.getCoid());
            map.put("senderName",order.getSenderName());
            map.put("senderPhone",order.getSenderPhone());
            map.put("departure",order.getDeparture());
            map.put("receiveName",order.getReceiveName());
            map.put("receivePhone",order.getReceivePhone());
            map.put("destination",order.getDestination());
            map.put("cargoType",order.getCargoType());
            map.put("weight",String.valueOf(order.getWeight()));
            map.put("volume",String.valueOf(order.getVolume()));
            map.put("cost",String.valueOf(order.getCost()));
            map.put("state",order.getState());
            if(order.getSubmitTime()!=null){
                map.put("submitTime",dateformat.format(order.getSubmitTime()));
            }else{
                map.put("submitTime","");
            }
            if(order.getSendTime()!=null){
                map.put("sendTime",dateformat.format(order.getSendTime()));
            }else{
                map.put("sendTime","");
            }
            if(order.getReceiveTime()!=null){
                map.put("receiveTime",dateformat.format(order.getReceiveTime()));
            }else{
                map.put("receiveTime","");
            }
            list.add(map);
        }
        int total = list.size();
        Layui l = Layui.data(total,list);
        return JSON.toJSONString(l);
    }

    // 货运公司界面————展示自己的全部订单
    @RequestMapping("/findOrderByCoid")
    @ResponseBody
    public String findOrderByCoid(HttpSession httpSession){
        String coid = (String) httpSession.getAttribute( "account");
        List<Order> orders = orderMapper.findOrderByCoid(coid);
        List<Map<String, String>> list = new ArrayList<>();
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
        for(Order order:orders){
            Map<String,String> map = new HashMap<>();
            map.put("oid",String.valueOf(order.getOid()));
            map.put("ceid",order.getCeid());
            map.put("senderName",order.getSenderName());
            map.put("senderPhone",order.getSenderPhone());
            map.put("departure",order.getDeparture());
            map.put("receiveName",order.getReceiveName());
            map.put("receivePhone",order.getReceivePhone());
            map.put("destination",order.getDestination());
            map.put("cargoType",order.getCargoType());
            map.put("weight",String.valueOf(order.getWeight()));
            map.put("volume",String.valueOf(order.getVolume()));
            map.put("cost",String.valueOf(order.getCost()));
            map.put("state",order.getState());
            if(order.getSubmitTime()!=null){
                map.put("submitTime",dateformat.format(order.getSubmitTime()));
            }else{
                map.put("submitTime","");
            }
            if(order.getSendTime()!=null){
                map.put("sendTime",dateformat.format(order.getSendTime()));
            }else{
                map.put("sendTime","");
            }
            if(order.getReceiveTime()!=null){
                map.put("receiveTime",dateformat.format(order.getReceiveTime()));
            }else{
                map.put("receiveTime","");
            }
            list.add(map);
        }
        int total = list.size();
        Layui l = Layui.data(total,list);
        return JSON.toJSONString(l);
    }

    // 货运公司界面————展示全部待接单
    @RequestMapping("/findOrderWaitReceive")
    @ResponseBody
    public String findOrderWaitReceive(){
        List<Order> orders = orderMapper.findOrderWaitReceive();
        List<Map<String, String>> list = new ArrayList<>();
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
        for(Order order:orders){
            Map<String,String> map = new HashMap<>();
            map.put("oid",String.valueOf(order.getOid()));
            map.put("ceid",order.getCeid());
            map.put("senderName",order.getSenderName());
            map.put("senderPhone",order.getSenderPhone());
            map.put("departure",order.getDeparture());
            map.put("receiveName",order.getReceiveName());
            map.put("receivePhone",order.getReceivePhone());
            map.put("destination",order.getDestination());
            map.put("cargoType",order.getCargoType());
            map.put("weight",String.valueOf(order.getWeight()));
            map.put("volume",String.valueOf(order.getVolume()));
            map.put("cost",String.valueOf(order.getCost()));
            map.put("state",order.getState());
            if(order.getSubmitTime()!=null){
                map.put("submitTime",dateformat.format(order.getSubmitTime()));
            }else{
                map.put("submitTime","");
            }
            list.add(map);
        }
        int total = list.size();
        Layui l = Layui.data(total,list);
        return JSON.toJSONString(l);
    }

    // 管理员界面————展示全部订单
    @RequestMapping("/showAllOrder")
    @ResponseBody
    public String showAllOrder(){
        List<Order> orders = orderMapper.showAllOrder();
        List<Map<String, String>> list = new ArrayList<>();
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
        for(Order order:orders){
            Map<String,String> map = new HashMap<>();
            map.put("oid",String.valueOf(order.getOid()));
            map.put("ceid",order.getCeid());
            map.put("coid",order.getCoid());
            map.put("senderName",order.getSenderName());
            map.put("senderPhone",order.getSenderPhone());
            map.put("departure",order.getDeparture());
            map.put("receiveName",order.getReceiveName());
            map.put("receivePhone",order.getReceivePhone());
            map.put("destination",order.getDestination());
            map.put("cargoType",order.getCargoType());
            map.put("weight",String.valueOf(order.getWeight()));
            map.put("volume",String.valueOf(order.getVolume()));
            map.put("cost",String.valueOf(order.getCost()));
            map.put("state",order.getState());
            if(order.getSubmitTime()!=null){
                map.put("submitTime",dateformat.format(order.getSubmitTime()));
            }else{
                map.put("submitTime","");
            }
            if(order.getSendTime()!=null){
                map.put("sendTime",dateformat.format(order.getSendTime()));
            }else{
                map.put("sendTime","");
            }
            if(order.getReceiveTime()!=null){
                map.put("receiveTime",dateformat.format(order.getReceiveTime()));
            }else{
                map.put("receiveTime","");
            }
            list.add(map);
        }
        int total = list.size();
        Layui l = Layui.data(total,list);
        return JSON.toJSONString(l);
    }

    // 通过订单id查找订单
    @RequestMapping("/findOrderByOid")
    @ResponseBody
    public String findOrderByOid(HttpSession httpSession){
        int oid = (int) httpSession.getAttribute("oid");
        Order order = orderMapper.findOrderByOid(oid);
        List<Map<String, String>> list = new ArrayList<>();
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
        // 存入map
        Map<String,String> map = new HashMap<>();
        //map.put("oid",String.valueOf(order.getOid()));
        map.put("ceid",order.getCeid());
        map.put("coid",order.getCoid());
        map.put("senderName",order.getSenderName());
        map.put("senderPhone",order.getSenderPhone());
        map.put("departure",order.getDeparture());
        map.put("receiveName",order.getReceiveName());
        map.put("receivePhone",order.getReceivePhone());
        map.put("destination",order.getDestination());
        map.put("cargoType",order.getCargoType());
        map.put("weight",String.valueOf(order.getWeight()));
        map.put("volume",String.valueOf(order.getVolume()));
        map.put("cost",String.valueOf(order.getCost()));
        map.put("state",order.getState());
        if(order.getSubmitTime()!=null){
            map.put("submitTime",dateformat.format(order.getSubmitTime()));
        }else{
            map.put("submitTime","");
        }
        if(order.getSendTime()!=null){
            map.put("sendTime",dateformat.format(order.getSendTime()));
        }else{
            map.put("sendTime","");
        }
        if(order.getReceiveTime()!=null){
            map.put("receiveTime",dateformat.format(order.getReceiveTime()));
        }else{
            map.put("receiveTime","");
        }
        list.add(map);

        int total = list.size();
        Layui l = Layui.data(total,list);
        return JSON.toJSONString(l);
    }

    // 检索不同货物的承运人账单
    @RequestMapping("/findOrderByCargotype")
    @ResponseBody
    public String findOrderByCargotype(@RequestParam("cargoType") String cargoType,HttpSession httpSession){
        String coid = (String) httpSession.getAttribute("account");
        List<Order> orders = orderMapper.findOrderByCargotype(cargoType,coid);
        List<Map<String, String>> list = new ArrayList<>();
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
        // 存入map
        for(Order order:orders) {
            Map<String, String> map = new HashMap<>();
            map.put("oid", String.valueOf(order.getOid()));
            map.put("ceid", order.getCeid());
            map.put("coid", order.getCoid());
            map.put("senderName", order.getSenderName());
            map.put("senderPhone", order.getSenderPhone());
            map.put("departure", order.getDeparture());
            map.put("receiveName", order.getReceiveName());
            map.put("receivePhone", order.getReceivePhone());
            map.put("destination", order.getDestination());
            map.put("cargoType", order.getCargoType());
            map.put("weight", String.valueOf(order.getWeight()));
            map.put("volume", String.valueOf(order.getVolume()));
            map.put("cost", String.valueOf(order.getCost()));
            map.put("state", order.getState());
            if (order.getSubmitTime() != null) {
                map.put("submitTime", dateformat.format(order.getSubmitTime()));
            } else {
                map.put("submitTime", "");
            }
            if (order.getSendTime() != null) {
                map.put("sendTime", dateformat.format(order.getSendTime()));
            } else {
                map.put("sendTime", "");
            }
            if (order.getReceiveTime() != null) {
                map.put("receiveTime", dateformat.format(order.getReceiveTime()));
            } else {
                map.put("receiveTime", "");
            }
            list.add(map);
        }

        int total = list.size();
        Layui l = Layui.data(total,list);
        return JSON.toJSONString(l);
    }


    // 承运商记录货物运输信息
    @ResponseBody //加这个注解，则直接返回数据，而不是模板路径
    @PostMapping("/addLogistics")
    public String addLogistics(@RequestParam("oid") int oid,@RequestParam("location") String location){
        Date now = new Date();
        Logistics logistics = new Logistics(oid,now,location);
        if(orderMapper.addLogistics(logistics)>0){
            return "提交物流状态成功";
        }else{
            return "提交物流状态失败";
        }
    }

    // 根据oid显示其所有物流信息
    @RequestMapping("/showAllLogisticsByOid")
    @ResponseBody
    public String showAllLogisticsByOid(@RequestParam("oid") int oid){
        List<Logistics> logistics_list = orderMapper.showAllLogisticsByOid(oid);
        List<Map<String, String>> list = new ArrayList<>();
        DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 存入map
        for(Logistics logistics:logistics_list) {
            Map<String, String> map = new HashMap<>();
            map.put("recordTime", dateformat.format(logistics.getRecordTime()));
            map.put("location", logistics.getLocation());
            list.add(map);
        }

        int total = list.size();
        Layui l = Layui.data(total,list);
        return JSON.toJSONString(l);
    }

}
