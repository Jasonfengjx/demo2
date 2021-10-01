package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.entity.Train;
import com.example.springboot.entity.Train;
import com.example.springboot.mapper.TrainMapper;

import com.example.springboot.mapper.TrainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/train")
public class TrainController {
    @Autowired
    TrainMapper trainMapper;
    @PostMapping
    @Transactional
    public Result<?> save(@RequestBody Train train){

        trainMapper.insert(train);
        return Result.success();
    }
    @PutMapping
    @Transactional
    public Result<?> update(@RequestBody Train train){
        LambdaQueryWrapper<Train> wrapper=Wrappers.<Train>lambdaQuery();
        wrapper.eq(Train::getTrainId,train.getTrainId());
        Train train1=trainMapper.selectOne(wrapper);
        if (train1==null){
           return save(train);
        }else {
            trainMapper.updateById(train);
            return Result.success();
        }


    }
    @DeleteMapping("/{trainId}")
    @Transactional
    public Result<?> delete(@PathVariable Integer trainId){

        trainMapper.deleteById(trainId);
        return Result.success();

    }
    @GetMapping
    @Transactional
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Train> wrapper= Wrappers.<Train>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Train::getTrainStart,search).or().like(Train::getTrainEnd,search);
        }
        Page<Train> TrainPage= trainMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(TrainPage);

    }
}
