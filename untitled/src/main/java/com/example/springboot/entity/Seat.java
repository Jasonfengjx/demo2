package com.example.springboot.entity;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @MppMultiId
    private String trainId;
    @MppMultiId
    private String roomId;
    private String seatType;
    private Integer seatSum;
    private String basicCost;
}
