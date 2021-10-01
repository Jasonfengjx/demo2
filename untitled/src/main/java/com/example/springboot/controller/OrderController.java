package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.domain.Order;
import com.example.springboot.domain.Statistic;
import com.example.springboot.mapper.OrderMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderMapper orderMapper;

    @PostMapping
    @Transactional
    public Result<?> save(@RequestBody Order order) {
        orderMapper.insert(order);
        return Result.success();
    }

    @PutMapping
    @Transactional
    public Result<?> update(@RequestBody Order order) {
        orderMapper.updateById(order);
        return Result.success();

    }

    @DeleteMapping("/{orderId}")
    @Transactional
    public Result<?> delete(@PathVariable Integer id) {
        orderMapper.deleteById(id);
        return Result.success();

    }

    @GetMapping
    @Transactional
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        //下边必须用一个你名列，可能是修改了吧哈哈哈哈妈了个巴子的
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Order::getTrainStart, search).or().like(Order::getTrainEnd, search);
        }
        Page<Order> userPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);

    }

    @GetMapping("/{phone}")
    @Transactional
    public Result<?> findPageByPhone(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @PathVariable("phone") String phone) {
        //下边必须用一个你名列，可能是修改了吧哈哈哈哈妈了个巴子的
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.eq(Order::getPhone, phone);
        Page<Order> userPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }
//  支付功能
    @PostMapping("/unpaid/{phone}")///order/unpaid/
    @Transactional
    public Result<?> UpdatePageByPhone(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestBody Order order,
                                       @PathVariable String phone) {
        order.setOrderState("未出行");
        orderMapper.updateById(order);
        return Result.success();
    }
//  获得某个用户未支付的订单
    @GetMapping("/unpaid/{phone}")//未支付
    @Transactional
    public Result<?> findPageByPhoneUnpaid(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable("phone") String phone) {
        //下边必须用一个你名列，可能是修改了吧哈哈哈哈妈了个巴子的
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.eq(Order::getPhone, phone);
        wrapper.eq(Order::getOrderState, "未支付");
        Page<Order> userPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }
//  支付本账户下所有的订单
    @PostMapping("/unpaid/1/{phone}")
    @Transactional
    public Result<?> UpdatePageByPhoneAll(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestBody Order order,
                                          @PathVariable String phone) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.eq(Order::getPhone,phone);
        wrapper.eq(Order::getOrderState,"未支付");
        List<Order> orders = orderMapper.selectList(wrapper);
        for (Order order1 : orders) {
            order1.setOrderState("未出行");
            orderMapper.updateById(order1);

        }
        return Result.success();
    }
//   获得未出行订单
    @GetMapping("/ungo/{phone}")
    @Transactional
    public Result<?> findPageByPhoneUngo(@RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @PathVariable("phone") String phone) {
        //下边必须用一个你名列，可能是修改了吧哈哈哈哈妈了个巴子的
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.eq(Order::getPhone, phone);
        wrapper.eq(Order::getOrderState, "未出行");
        Page<Order> userPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

//    未出行订单，退票功能
    @PostMapping("/ungo/{phone}")
    @Transactional
    public Result<?> UpdatePageByPhoneUnGo(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestBody Order order) {
        order.setOrderId(order.getOrderId());
        order.setOrderState("退票");
        orderMapper.updateById(order);
        return Result.success();
    }

    //    未出行订单，改签
    @PostMapping("/ungo/change/{phone}")
    @Transactional
    public Result<?> UpdatePageByPhoneUnGoChange(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestBody Order order) {
        order.setOrderId(order.getOrderId());
        order.setOrderState("改签");
        orderMapper.updateById(order);
        return Result.success();
    }
    @GetMapping("/Statistic")
    @Transactional
    public Result<?> getStatisticData(){
        List<Statistic> statisticList=new ArrayList<Statistic>();
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        Statistic statistic = new Statistic("完成",orderMapper.getOver(),
                nt.format(Double.valueOf(orderMapper.getOver()) /orderMapper.getAll()));
        statisticList.add(statistic);
        Statistic statistic1 = new Statistic("退票",orderMapper.getQuit(),
                nt.format(Double.valueOf(orderMapper.getQuit()) /orderMapper.getAll()));
        statisticList.add(statistic1);
        Statistic statistic2 = new Statistic("改签",orderMapper.getChange(),
                nt.format(Double.valueOf(orderMapper.getChange()) /orderMapper.getAll()));
        statisticList.add(statistic2);
        Statistic statistic3 = new Statistic("未支付",orderMapper.getUnpaid(),
                nt.format(Double.valueOf(orderMapper.getUnpaid()) /orderMapper.getAll()));
        statisticList.add(statistic3);
        Statistic statistic4 = new Statistic("未出行",orderMapper.getUnOver(),
                nt.format(Double.valueOf(orderMapper.getUnOver()) /orderMapper.getAll()));
        statisticList.add(statistic4);
        Statistic statistic5 = new Statistic("取消",orderMapper.getQuxiao(),
                nt.format(Double.valueOf(orderMapper.getQuxiao()) /orderMapper.getAll()));
        statisticList.add(statistic5);
        Statistic statistic6 = new Statistic("总共",orderMapper.getAll(),
                nt.format(Double.valueOf(orderMapper.getAll()) /orderMapper.getAll()));
        statisticList.add(statistic6);
        return Result.success(statisticList);
    }

}
