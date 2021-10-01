package com.example.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainTransfer {
    private String trainNo1;
    private String trainNumber1;
    private String trainNo2;
    private String trainNumber2;
    private String startStationNo;
    private String startStationName;
    private String transferStationNo1;
    private String transferStationName;
    private String startTime1;
    private String arriveTime1;
    private String startTime2;
    private String arriveTime2;
    private String endStationNo;
    private String endStationName;
    private String startRunningTime1;
    private String endRunningTime1;
    private String startRunningTime2;
    private String endRunningTime2;
    private String transferStationNo2;


    public Boolean standardOrNot() throws ParseException {
        //判断头车的到达时间是否小于后车
        DateFormat format1=new SimpleDateFormat("HH:mm:ss");
        Date date1=format1.parse(arriveTime1);
        Date date2=format1.parse(startTime2);
        if (date1.before(date2)){
            return true;
        }else {
            return false;
        }



    }
}
