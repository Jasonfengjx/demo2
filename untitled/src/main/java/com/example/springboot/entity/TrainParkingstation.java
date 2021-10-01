package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@TableName("train_parkingstation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainParkingstation {

    private String trainId;
    private String trainNumber;
    private String arriveDay;
    private String startTime;
    private String arriveTime;
    private String runningTime;
    private Integer stationNo;
    private String stationName;
}
