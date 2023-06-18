package com.zhengyuan.liunao.entity;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Income",description = "承运商公司月收入")
public class Income {
    private String companyID;
    private int yearMonth;
    private double income;
//    private double money;
//    private int year;

    public Income(String companyID, int yearMonth, double income) {
        this.companyID = companyID;
        this.yearMonth = yearMonth;
        this.income = income;
    }
    public Income(){
        super();
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public int getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(int yearMonth) {
        this.yearMonth = yearMonth;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }



}
