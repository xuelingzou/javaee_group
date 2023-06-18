package com.example.auto_warehouse.bean;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import java.sql.Timestamp;

import java.util.Date;

public class Logistics {


    public Logistics(int oid, Date recordTime, String location) {
        this.oid = oid;
        this.recordTime = recordTime;
        this.location = location;
    }

    private int oid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    private String location;

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Logistics(int oid, String location) {
        this.oid = oid;
        this.location = location;
    }
    public Logistics(){

    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
