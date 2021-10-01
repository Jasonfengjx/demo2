package com.example.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPassenger {


    private String passengerName;
    private String passengerIdentity;
    private String passengerType;
    private String passengerPhone;
    private String phone;
    private String identityType;

    private Integer orderId;
    private String orderState;
    private String orderType;
    private String orderTime;


    private String trainId;
    private Integer roomId;
    private String seatId;
    private String seatType;
    private String price;
    private String trainStart;
    private String trainEnd;
    private String trainDate;
    private String trainStarttime;
    private String trainEndtime;

}
