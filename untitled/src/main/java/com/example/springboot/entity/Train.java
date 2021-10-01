package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Train  {
    @TableId
    private String trainId;
    private String trainType;
    private String trainRoom;
    private String trainStart;
    private String trainEnd;
    private String trainStarttime;
    private String trainEndtime;
    private String trainName;
    private String arriveType;
    private String runningTime;
    private String state;
}
