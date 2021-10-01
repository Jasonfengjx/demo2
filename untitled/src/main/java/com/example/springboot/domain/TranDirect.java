package com.example.springboot.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;

/**
 * 列车查询类，
 * 直达车查询，
 * 用于根据始发站和终点站查询到合适的列车
 * 之后将数据返回给前端
 * 先写接口后写前端
 * */
@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranDirect {
    private String trainNo;
    private String trainNumber;
    private String startStation;
    private String endStation;
    private String startNo;
    private String endNo;
    private String startTime;
    private String arriveTime;
    private String startRunningTime;
    private String endRunningTime;
}
