package com.example.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order1")
public class Order {
    @TableId(value =  "order_id223",type=IdType.AUTO)
    private Integer orderId;
    private String orderState;
    private String orderType;
    private String orderTime;
    private String phone;
    private String passengerName;
    private String passengerIdentity;
    private String trainId;
    private Integer roomId;
    private String seatId;
    private String price;
    private String trainStart;
    private String trainEnd;
    private String trainDate;
    private String trainStarttime;
    private String trainEndtime;
}
