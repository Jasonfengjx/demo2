package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.entity.Seat;
import com.example.springboot.mapper.SeatMapper;
import com.example.springboot.mapper.TrainDirectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    SeatMapper seatMapper;
    @Autowired
    TrainDirectMapper trainDirectMapper;

    @PostMapping
    @Transactional
    public Result<?> save(@RequestBody Seat seat) {
        LambdaQueryWrapper<Seat> wrapper = Wrappers.<Seat>lambdaQuery();
        wrapper.eq(Seat::getTrainId, seat.getTrainId()).eq(Seat::getRoomId, seat.getRoomId());
        Seat seat1 = seatMapper.selectOne(wrapper);
        if (seat1 != null) {
            update(seat);
        } else {
            seatMapper.insert(seat);
        }

        return Result.success();
    }

    @PutMapping
    @Transactional
    public Result<?> update(@RequestBody Seat seat) {
        LambdaQueryWrapper<Seat> wrapper = Wrappers.<Seat>lambdaQuery();
        wrapper.eq(Seat::getTrainId, seat.getTrainId()).eq(Seat::getRoomId, seat.getRoomId());
        seatMapper.update(seat, wrapper);
        return Result.success();
    }
    @DeleteMapping("/{trainId}/{roomId}")
    @Transactional
    public Result<?> delete(@PathVariable String trainId, @PathVariable Integer roomId) {
        LambdaQueryWrapper<Seat> wrapper = Wrappers.<Seat>lambdaQuery();
        wrapper.eq(Seat::getTrainId, trainId).eq(Seat::getRoomId, roomId);
        seatMapper.delete(wrapper);
        return Result.success();
    }

    @GetMapping
    @Transactional
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Seat> wrapper = Wrappers.<Seat>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Seat::getTrainId, search).or().like(Seat::getRoomId, search);
        }
        Page<Seat> seatPage = seatMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(seatPage);
    }

    @GetMapping("/{trainNo}")
    @Transactional
    public Result<?> findPageByTrain(@PathVariable("trainNo") String trainNo,
                                     @RequestParam("startStationNo") String startStationNo,
                                     @RequestParam("endStationNo") String endStationNo) {
        LambdaQueryWrapper<Seat> wrapper = Wrappers.<Seat>lambdaQuery();
        wrapper.eq(Seat::getTrainId,trainNo);
        List<Seat> seatList=seatMapper.findSeats(trainNo);
        Integer Num=Integer.parseInt(endStationNo)-Integer.parseInt(startStationNo)-0;
        for (Seat seat : seatList) {
            seat.setBasicCost(String.valueOf(Double.parseDouble(seat.getBasicCost())*Num));
        }
        return Result.success(seatList);
    }

}
