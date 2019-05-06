package com.jex.official.entity.db;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    public final static int STATUS_NOT_PAY = 0;
    public final static int STATUS_PAY_COMPLETE = 1;
    public final static int UNKNOW_TYPE_PAY = 0;
    public final static int WECHAT_TYPE_PAY = 1;
    public final static int ALIPAY_TYPE_PAY = 2;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "open_id")
    private String openId = "";
    @Column(name = "activity_id")
    private int activityId = 0;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "price")
    private int price = 0;
    @Column(name = "name")
    private String name = "";
    @Column(name = "phone")
    private String phone = "";
    @Column(name = "address")
    private String address = "";
    @Column(name = "shipping_code")
    private String shippingCode = "";
    @Column(name="status")
    private int status = STATUS_NOT_PAY;
    @Column(name = "pay_type")
    private int payType = UNKNOW_TYPE_PAY;
    @Column(name = "trade_no")
    private String tradeNo= "";
    @Column(name = "payment_time")
    private LocalDateTime paymentTime;
    @Column(name = "remark")
    private String remark = "";
    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public int getId() {
        return id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
