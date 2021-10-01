package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.entity.Trainschedule;
import com.example.springboot.mapper.TrainscheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ts")
@RestController
public class TrainscheduleController {
    @Autowired
    TrainscheduleMapper trainscheduleMapper;

    @PostMapping
    @Transactional
    public Result<?> save(@RequestBody Trainschedule trainschedule) {

        trainscheduleMapper.insert(trainschedule);
        return Result.success();
    }

    @PutMapping
    @Transactional
    public Result<?> update(@RequestBody Trainschedule trainschedule) {

        trainscheduleMapper.updateById(trainschedule);
        return Result.success();

    }

    @DeleteMapping("/{id}")
    @Transactional
    public Result<?> delete(@PathVariable Integer id) {
        trainscheduleMapper.deleteById(id);
        return Result.success();

    }

    @GetMapping
    @Transactional
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<Trainschedule> wrapper = Wrappers.<Trainschedule>lambdaQuery();

        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Trainschedule::getDepartureStation, search)
                    .or().like(Trainschedule::getEndStation, search)
                    .or().like(Trainschedule::getFromStation, search)
                    .or().like(Trainschedule::getToStation, search);
        }
        Page<Trainschedule> trainschedulePage = trainscheduleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(trainschedulePage);
    }

    @GetMapping("/a")
    @Transactional
    public Result<?> findPageUser(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String search,
                                  @RequestParam(defaultValue = "") String searchS,
                                  @RequestParam(defaultValue = "") String searchE
    ) {

        LambdaQueryWrapper<Trainschedule> wrapper = Wrappers.<Trainschedule>lambdaQuery();
        if (StrUtil.isNotBlank(search)&&(StrUtil.isNotBlank(searchS))&&(StrUtil.isNotBlank(searchE))) {
            wrapper.eq(Trainschedule::getTrainNumber, search).eq(Trainschedule::getDepartureStation,searchS).eq(Trainschedule::getEndStation,searchE);
        }else {
            wrapper.like(Trainschedule::getTrainNumber, search).like(Trainschedule::getDepartureStation,searchS).like(Trainschedule::getEndStation,searchE);
        }
        Page<Trainschedule> trainschedulePage = trainscheduleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(trainschedulePage);
    }
    @GetMapping("/b")
    @Transactional
    public Result<?> findPageUser2(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String search,
                                  @RequestParam(defaultValue = "") String searchL,
                                  @RequestParam(defaultValue = "") String searchN
    ) {

        LambdaQueryWrapper<Trainschedule> wrapper = Wrappers.<Trainschedule>lambdaQuery();
        if (StrUtil.isNotBlank(search)&&(StrUtil.isNotBlank(searchL))&&(StrUtil.isNotBlank(searchN))) {
            wrapper.eq(Trainschedule::getTrainNumber, search).eq(Trainschedule::getFromStation,searchL).eq(Trainschedule::getToStation,searchN);
        }else {
            wrapper.like(Trainschedule::getTrainNumber, search).like(Trainschedule::getFromStation,searchL).like(Trainschedule::getToStation,searchN);
        }
        Page<Trainschedule> trainschedulePage = trainscheduleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(trainschedulePage);
    }
}
