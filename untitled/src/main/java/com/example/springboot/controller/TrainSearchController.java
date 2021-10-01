package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.domain.TrainTransfer;
import com.example.springboot.domain.TranDirect;
import com.example.springboot.entity.Trainschedule;
import com.example.springboot.mapper.TrainDirectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/trainsearch")
public class TrainSearchController {
    @Autowired
    TrainDirectMapper trainDirectMapper;
    @GetMapping("/direct")
    @Transactional
    public Result<?> findPageDirect(@RequestParam(defaultValue = "") String searchS,
                                    @RequestParam(defaultValue = "") String searchE
    ) {
        List<TranDirect> directTrain=trainDirectMapper.getDirectTrain(searchS,searchE);
        return Result.success(directTrain);
    }
    @GetMapping("/viastation")
    @Transactional
    public Result<?> findPageViastation(@RequestParam(defaultValue = "") String searchS,
                                    @RequestParam(defaultValue = "") String searchE
    ) {
        List<TranDirect> directTrain=trainDirectMapper.getDirectTrain(searchS,searchE);
        return Result.success(directTrain);
    }

    @GetMapping("/transfer")
    @Transactional
    public Result<?> findPageTransfer(@RequestParam(defaultValue = "") String searchS,
                                    @RequestParam(defaultValue = "") String searchE
    ) throws ParseException {
        List<TrainTransfer> transferTrain=trainDirectMapper.getTranserTrain(searchS,searchE);
//        if(transferTrain!=null){
//            for (TrainTransfer each: transferTrain){
//                if(each.standardOrNot()){
//                    transferTrain.remove(each);
//                }
//            }
//        }

        return Result.success(transferTrain);
    }


}
