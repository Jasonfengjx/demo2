package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.entity.Station;
import com.example.springboot.mapper.StationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/station")
public class StationController {
    @Autowired
    StationMapper stationMapper;
    @PostMapping
    @Transactional
    public Result<?> save(@RequestBody Station station){
        stationMapper.insert(station);

        return Result.success();
    }
    @PutMapping
    @Transactional
    public Result<?> update(@RequestBody Station station){

        stationMapper.updateById(station);
        return Result.success();

    }
    @DeleteMapping("/{id}")
    @Transactional
    public Result<?> delete(@PathVariable Integer id){

        stationMapper.deleteById(id);
        return Result.success();

    }
    @GetMapping
    @Transactional
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Station> wrapper= Wrappers.<Station>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Station::getStationName,search).or().like(Station::getCity,search);
        }
        Page<Station> StationPage= stationMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(StationPage);

    }

}
