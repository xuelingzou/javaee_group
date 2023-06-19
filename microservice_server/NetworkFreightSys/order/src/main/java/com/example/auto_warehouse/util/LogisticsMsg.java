package com.example.auto_warehouse.util;
import com.google.common.base.MoreObjects;

public class LogisticsMsg {
    /** 消息类型：更新物流信息，值为: {@value} */
    public static final String MA_UPDATE = "update";

    // fields =================================================================
    private String action;
    private String itemCode;

    // constructor ============================================================
    public LogisticsMsg() {  }

    public LogisticsMsg(String action, String itemCode) {
        this.action = action;
        this.itemCode = itemCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("action", this.getAction())
                .add("itemCode", this.getItemCode()).toString();
    }

    // setter/getter ====================================================
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}
