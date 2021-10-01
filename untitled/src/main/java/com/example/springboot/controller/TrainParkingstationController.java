package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.entity.TrainParkingstation;
import com.example.springboot.mapper.TrainParkingstationMapper;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/trainInfo")
@RestController
public class TrainParkingstationController {
    @Autowired
    TrainParkingstationMapper trainParkingstationMapper;
    @PostMapping
    @Transactional
    public Result<?> save(@RequestBody TrainParkingstation trainParkingstation){

        trainParkingstationMapper.insert(trainParkingstation);
        return Result.success();
    }
    @PutMapping
    @Transactional
    public Result<?> update(@RequestBody TrainParkingstation trainParkingstation){
        LambdaQueryWrapper<TrainParkingstation> wrapper=Wrappers.<TrainParkingstation>lambdaQuery();
        wrapper.eq(TrainParkingstation::getTrainId,trainParkingstation.getTrainId())
                .eq(TrainParkingstation::getTrainNumber,trainParkingstation.getTrainNumber())
                .eq(TrainParkingstation::getStationNo,trainParkingstation.getStationNo());
        TrainParkingstation trainParkingstation1=trainParkingstationMapper.selectOne(wrapper);
        if (trainParkingstation1==null){
           return save(trainParkingstation);
        }else{
            System.out.println(trainParkingstation.getStationName());
            trainParkingstationMapper.update(trainParkingstation,wrapper);
            return Result.success();
        }
    }
    @DeleteMapping("/{trainNo}/{trainNumber}/{stationNo}")
    @Transactional
    public Result<?> delete(@PathVariable String trainNo, @PathVariable String trainNumber, @PathVariable Integer stationNo){

        LambdaQueryWrapper<TrainParkingstation> wrapper=Wrappers.<TrainParkingstation>lambdaQuery();
        wrapper.eq(TrainParkingstation::getTrainId,trainNo)
                .eq(TrainParkingstation::getTrainNumber, trainNumber)
                .eq(TrainParkingstation::getStationNo,stationNo);
        trainParkingstationMapper.delete(wrapper);
        return Result.success();

    }
    @GetMapping
    @Transactional
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<TrainParkingstation> wrapper= Wrappers.<TrainParkingstation>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(TrainParkingstation::getTrainNumber,search).or().like(TrainParkingstation::getStationName,search);
        }
        Page<TrainParkingstation> TrainParkingstationPage= trainParkingstationMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(TrainParkingstationPage);

    }
}
