package com.zhengyuan.liunao.controller.dealcontroller;


import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.zhengyuan.liunao.entity.Company;
import com.zhengyuan.liunao.entity.Income;
import com.zhengyuan.liunao.entity.Logistics;
import com.zhengyuan.liunao.entity.Order;
import com.zhengyuan.liunao.repository.OrderMapper;
import com.zhengyuan.liunao.service.CompanyService;
import com.zhengyuan.liunao.service.IncomeService;
//import com.zhengyuan.liunao.tools.JsonResult;
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

    @Autowired
    CompanyService companyService;
    // 客户提交订单
    @ResponseBody //加这个注解，则直接返回数据，而不是模板路径
    @PostMapping("/v1/orders/{ceid}")
    public JsonResult<Order> submitOrder(@PathVariable("ceid") String ceid, @RequestBody Order order) throws ParseException {
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
        //String ceid = (String) httpSession.getAttribute( "account");
        System.out.println(ceid);
        order.setCeid(ceid); //客户id
        order.setCost(order.getWeight(),order.getVolume());
        if(orderMapper.addOrder(order)>0){
            return new JsonResult<>(HttpStatus.HTTP_CREATED,"提交订单成功",order);
        }else{
            //return "提交订单失败";
            return new JsonResult<>(HttpStatus.HTTP_INTERNAL_ERROR,"提交订单失败");
        }

    }

    // 货运公司接单/发货/送达，更新状态
    @ResponseBody
    @PostMapping("/v1/orders/{oid}/state")
    public JsonResult<Order> updateOrderState(@PathVariable("oid") String oid1, @RequestBody Map<String,String> map) throws ParseException {
        int oid = Integer.parseInt(oid1);
        String coid = map.get("coid");
        // 接单
        if(map.get("state").equals("接单")){
            // 获取当前货运公司的coid
//            String coid = map.get("coid");
            // 对数据库的操作：更改数据库中该条order的状态，设定关联货运公司
            int num = orderMapper.updateCoidNState(oid, coid);
            if(num==0){
                return new JsonResult<>(HttpStatus.HTTP_NOT_FOUND,"未找到该订单");
            }

            // 更新income表
            double cost = orderMapper.findOrderByOid(oid).getCost();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int yearMonth = year*100+month+1;
            List<Income> list = incomeService.findByYearMonth(coid,yearMonth);
            int num1 = 0;
            if(list.size()==0){
                Income income = new Income(coid,yearMonth,cost);
                num1 = incomeService.insertIncome(income);
            }else{
                num1 = incomeService.updateIncome(yearMonth,cost,coid);
            }
            if(num1==0){
                return new JsonResult<>(HttpStatus.HTTP_INTERNAL_ERROR,"更新状态失败");
            }
            Order order = orderMapper.findOrderByOid(oid);

            /*更新物流信息*/
            //获取承运商名称
            List<Company> companyList =  companyService.getCompanyByNum(coid);
            Company company = companyList.get(0);
            String coName = company.getCoName();

            Date now = new Date();
            String location = "承运商"+coName+"已接单";
            Logistics logistics = new Logistics(oid,now,location);
            orderMapper.addLogistics(logistics); //更新物流信息

            return new JsonResult<>(HttpStatus.HTTP_OK,"接单成功",order);

        }else if(map.get("state").equals("发货")){
            // 获取当前时间
            Date now = new Date();
            SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date sendTime = tFormat.parse(tFormat.format(now));
            // 修改数据库对应数据
            int num=orderMapper.updateSendTNState(oid, sendTime);
            if(num==0){
                return new JsonResult<>(HttpStatus.HTTP_NOT_FOUND,"未找到该订单");
            }
            Order order = orderMapper.findOrderByOid(oid);

            /*更新物流信息*/
            //获取承运商名称
            List<Company> companyList =  companyService.getCompanyByNum(coid);
            Company company = companyList.get(0);
            String coName = company.getCoName();

            String location = "承运商"+coName+"已发货";
            Logistics logistics = new Logistics(oid,now,location);
            orderMapper.addLogistics(logistics); //更新物流信息


            return new JsonResult<>(HttpStatus.HTTP_OK,"发货成功",order);
        }else if(map.get("state").equals("送达")){
            // 获取当前时间
            Date now = new Date();
            SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date receiveTime = tFormat.parse(tFormat.format(now));
            // 修改数据库对应数据
            int num=orderMapper.updateReceiveTNState(oid, receiveTime);
            if(num==0){
                return new JsonResult<>(HttpStatus.HTTP_NOT_FOUND,"未找到该订单");
            }
            Order order = orderMapper.findOrderByOid(oid);

            /*更新物流信息*/
            String location = "货物已送达";
            Logistics logistics = new Logistics(oid,now,location);
            orderMapper.addLogistics(logistics); //更新物流信息



            return new JsonResult<>(HttpStatus.HTTP_OK,"货物已送达",order);
        }else{
            return new JsonResult<>(HttpStatus.HTTP_BAD_REQUEST,"参数state不合法");
        }

    }


    // 客户界面————展示自己的全部订单
    @GetMapping("/v1/orders/ceid/{ceid}")
    @ResponseBody
//    public JsonResult<Object> findOrderByCeid(@PathVariable("ceid") String ceid,HttpSession httpSession){
    public String findOrderByCeid(@PathVariable("ceid") String ceid){

//        String ceid = (String) httpSession.getAttribute( "account");
        System.out.println(ceid);
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
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", 0);
//        map.put("msg", "");
//        map.put("count",total);
//        map.put("data",list);
//        return new JsonResult<>( map);

        int total = list.size();
//        Layui l = Layui.data(HttpStatus.HTTP_OK,"",total,list);
        Layui l = Layui.data(Integer.toString(HttpStatus.HTTP_OK) ,total,list);
        return JSON.toJSONString(l);
//        return new JsonResult<>(HttpStatus.HTTP_OK,l);
    }

    // 货运公司界面————展示自己的全部订单
    @GetMapping("/v1/orders/coid/{coid}")
    @ResponseBody
    public String findOrderByCoid(@PathVariable("coid") String coid){
        //String coid = (String) httpSession.getAttribute( "account");
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
        Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total,list);
        return JSON.toJSONString(l);
        //return new JsonResult<>(HttpStatus.HTTP_OK,list);
    }

    // 货运公司界面————展示全部待接单
    @GetMapping("/v1/orders/waiting")
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
        //return new JsonResult<>(HttpStatus.HTTP_OK,list);
        int total = list.size();
        Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total,list);
        return JSON.toJSONString(l);
    }

    // 管理员界面————展示全部订单
    @GetMapping("/v1/orders")
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
        Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total,list);
        return JSON.toJSONString(l);
        //return new JsonResult<>(HttpStatus.HTTP_OK,list);
    }

    // 通过订单id查找订单
    @GetMapping("/v1/orders/{oid}")
    @ResponseBody
    public String findOrderByOid(@PathVariable("oid") String oid1){
        //int oid = (int) httpSession.getAttribute("oid");
        int oid = Integer.parseInt(oid1);
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
        Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total,list);
        return JSON.toJSONString(l);
        //return new JsonResult<>(HttpStatus.HTTP_OK,list);
    }

    // 检索不同货物的承运人账单
    @GetMapping("/v1/orders/cargoType/{cargoType}")
    @ResponseBody
    public String findOrderByCargotype(@PathVariable("cargoType") String cargoType,@RequestParam("coid")String coid){
        //String coid = map1.get("coid");
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
        Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total,list);
        return JSON.toJSONString(l);
        //return new JsonResult<>(HttpStatus.HTTP_OK,list);
    }


    // 承运商记录货物运输信息
    @ResponseBody //加这个注解，则直接返回数据，而不是模板路径
    @PostMapping("/v1/logistics")
//    public JsonResult<Logistics> addLogistics(@RequestParam("oid")String oid1,@RequestParam("location")String location){
    public JsonResult<Logistics> addLogistics(@RequestBody Map<String,String> map1){
        String oid1 = map1.get("oid");
        String location = map1.get("location");
        int oid = Integer.parseInt(oid1);
        Date now = new Date();
        Logistics logistics = new Logistics(oid,now,location);
        if(orderMapper.addLogistics(logistics)>0){
            return new JsonResult<>(HttpStatus.HTTP_CREATED,"提交物流状态成功");
        }else{
            return new JsonResult<>(HttpStatus.HTTP_INTERNAL_ERROR,"提交物流状态失败");
        }
    }

    // 根据oid显示其所有物流信息
    @GetMapping("/v1/logistics/{oid}")
    @ResponseBody
    public String showAllLogisticsByOid(@PathVariable("oid") String oid){
        List<Logistics> logistics_list = orderMapper.showAllLogisticsByOid(Integer.parseInt(oid));
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
        Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total,list);
        return JSON.toJSONString(l);
        //return new JsonResult<>(HttpStatus.HTTP_OK,list);
    }

}
