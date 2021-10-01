package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("passenger")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    private String passengerName;
    @TableId
    private String passengerIdentity;
    private String passengerType;
    private String passengerPhone;
    private String phone;
    private String identityType;
}
