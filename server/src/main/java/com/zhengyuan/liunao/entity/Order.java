package com.zhengyuan.liunao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    int oid;             // 订单id（自增1）
    String ceid;         // 客户id
    String coid;         // 货运公司id

    String senderName;   // 发件人名
    String senderPhone;  // 发件人电话
    String departure;    // 发件地址

    String receiveName;  // 收件人名
    String receivePhone; // 收件人电话
    String destination;  // 收件地址

    String cargoType;    // 货物种类
    double weight;       // 货物重量
    double volume;       // 货物体积
    double cost;         // 订单花销
    String state;        // 订单状态（待接单/已接单/已到达）

    Date submitTime;     // 提交订单时间
    Date sendTime;       // 发货时间
    Date receiveTime;    // 到达时间

    public Order(String ceid, String senderName, String senderPhone, String departure, String receiveName, String receivePhone, String destination, String cargoType, double weight, double volume) throws ParseException {
        this.ceid = ceid;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.departure = departure;
        this.receiveName = receiveName;
        this.receivePhone = receivePhone;
        this.destination = destination;
        this.cargoType = cargoType;
        this.weight = weight;
        this.volume = volume;
        setCost(weight, volume);    // cost需要根据weight和volume计算
        setState("待接单");          // 默认提交订单时，状态为待接单
        setSubmitTime();            // 提交时间系统自动生成
    }

    public Order() throws ParseException{

        setState("待接单");
        setSubmitTime();
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getCeid() {
        return ceid;
    }

    public void setCeid(String ceid) {
        this.ceid = ceid;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double weight, double volume) {
        this.cost = weight + volume;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime() throws ParseException {
        Date now = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.submitTime = tFormat.parse(tFormat.format(now));
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    //    为了打印对象方便
    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", ceid='" + ceid + '\'' +
                ", coid='" + coid + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", departure='" + departure + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", destination='" + destination + '\'' +
                ", cargoType='" + cargoType + '\'' +
                ", weight=" + weight +
                ", volume=" + volume +
                ", cost=" + cost +
                ", state='" + state + '\'' +
                ", submitTime=" + submitTime +
                ", sendTime=" + sendTime +
                ", receiveTime=" + receiveTime +
                '}';
    }

}

