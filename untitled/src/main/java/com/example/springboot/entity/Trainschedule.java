package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("trainschedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainschedule {
    @TableId
    private Integer id;
    private String trainId;
    private String trainNumber;
    private String departureStation;
    private String endStation;
    private String fromStation;
    private String toStation;
    private String departureTime;
    private String arrivalTime;
    private String useTime;
    private String businessSeat;
    private String firstClassSeats;
    private String secondClassSeats;
    private String highGradeSoftBerth;
    private String softBedFirstClassBed;
    private String motorTrainBed;
    private String hardSecondClassBed;
    private String softSeats;
    private String hardSeat;
    private String seatless;
}
