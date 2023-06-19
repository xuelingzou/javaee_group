package com.example.auto_warehouse.controller;

import com.example.auto_warehouse.bean.Logistics;
import com.example.auto_warehouse.service.LogisticsService;
import com.example.auto_warehouse.util.LogisticsMsg;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.Date;

@EnableBinding(Sink.class)
@Slf4j
public class LogisticsMsgListener {
    // 物流消息监听器

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LogisticsService logisticsService;

    @StreamListener(Sink.INPUT)
    public void onLogisticsMsg(LogisticsMsg logisticsMsg) {
        log.info("sleuth跟踪日志");
        if (LogisticsMsg.MA_UPDATE.equalsIgnoreCase(logisticsMsg.getAction())) {
            this.logger.info("收到物流更新消息，参数: {}", logisticsMsg.getItemCode());
            String s = logisticsMsg.getItemCode();
            String oid1 = s.split(",")[0];
            String location = s.split(",")[1];
            int oid = Integer.parseInt(oid1);
            Date now = new Date();
            Logistics logistics = new Logistics(oid,now,location);
            if(logisticsService.addLogistics(logistics)>0){
                this.logger.info("成功更新物流信息");
            }else{
                this.logger.error("更新物流信息失败！");
            }
        }else{
            this.logger.warn("收到未知物流消息: {}", logisticsMsg);
        }
    }
}
