package com.example.auto_warehouse.controller;


import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;

//import com.zhengyuan.liunao.tools.JsonResult;
import com.example.auto_warehouse.bean.Logistics;
import com.example.auto_warehouse.mapper.LogisticsMapper;
import com.example.auto_warehouse.service.LogisticsService;

import com.example.auto_warehouse.util.JsonResult;
import com.example.auto_warehouse.util.Layui;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@RestController
@RequestMapping("/Sys")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;



    // 承运商记录货物运输信息
    @ResponseBody //加这个注解，则直接返回数据，而不是模板路径
    @PostMapping("/v1/logistics/{s}")
//    public JsonResult<Logistics> addLogistics(@RequestParam("oid")String oid1,@RequestParam("location")String location){
    public Mono<String> addLogistics(@PathVariable("s") String s){
        log.info("sleuth跟踪日志");

        String oid1 = s.split(",")[0];
        String location = s.split(",")[1];
        int oid = Integer.parseInt(oid1);
        Date now = new Date();
        Logistics logistics = new Logistics(oid,now,location);
        Mono<String> stringMono;
        if(logisticsService.addLogistics(logistics)>0){
            stringMono = Mono.just("true");
        }else{
            stringMono = Mono.just("false");
        }
        return stringMono;
    }

    // 根据oid显示其所有物流信息
    @GetMapping("/v1/logistics/{oid}")
    @ResponseBody
    public Mono<String> showAllLogisticsByOid(@PathVariable("oid") String oid){
        log.info("sleuth跟踪日志");
        List<Logistics> logistics_list = logisticsService.showAllLogisticsByOid(Integer.parseInt(oid));
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
        Mono<String> stringMono = Mono.just(JSON.toJSONString(l));
        return stringMono;
        //return new JsonResult<>(HttpStatus.HTTP_OK,list);
    }

}
