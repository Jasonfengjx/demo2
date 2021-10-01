package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.domain.Order;
import com.example.springboot.domain.OrderPassenger;
import com.example.springboot.entity.Passenger;
import com.example.springboot.entity.Seat;
import com.example.springboot.entity.Train;
import com.example.springboot.mapper.OrderMapper;
import com.example.springboot.mapper.PassengerMapper;
import com.example.springboot.mapper.SeatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    PassengerMapper passengerMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    SeatMapper seatMapper;

    @PostMapping
    @Transactional
    public Result<?> save(@RequestBody Passenger passenger) {
        if (passenger.getPhone() == null) {
            passenger.setPhone("15688839773");
        }
        passengerMapper.insert(passenger);
        return Result.success();
    }

    @PutMapping
    @Transactional
    public Result<?> update(@RequestBody Passenger passenger) {
        LambdaQueryWrapper<Passenger> wrapper = Wrappers.<Passenger>lambdaQuery();
        wrapper.eq(Passenger::getPassengerIdentity, passenger.getPassengerIdentity());
        Passenger passenger1 = passengerMapper.selectOne(wrapper);
        if (passenger1 == null) {
            return save(passenger);
        } else {
            passengerMapper.updateById(passenger);
            return Result.success();
        }
    }

    @DeleteMapping("/{passengerIdentity}")
    @Transactional
    public Result<?> delete(@PathVariable String passengerIdentity) {
        LambdaQueryWrapper<Passenger> wrapper = Wrappers.<Passenger>lambdaQuery();
        wrapper.eq(Passenger::getPassengerIdentity, passengerIdentity);
        passengerMapper.delete(wrapper);
        return Result.success();
    }

    @GetMapping
    @Transactional
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize
            , @RequestParam(defaultValue = "") String phone) {
        LambdaQueryWrapper<Passenger> wrapper = Wrappers.<Passenger>lambdaQuery();
//        wrapper.like(Passenger::getPassengerName, phone);
        wrapper.eq(Passenger::getPhone,phone);
        Page<Passenger> PassengerPage = passengerMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(PassengerPage);
    }

    @GetMapping("/order")
    @Transactional
    public Result<?> findPageOrder(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "") String phone) {
        LambdaQueryWrapper<Passenger> wrapper = Wrappers.<Passenger>lambdaQuery();
//        wrapper.like(Passenger::getPassengerName, phone);
        wrapper.eq(Passenger::getPhone,phone);
        System.out.println(phone);
        Page<Passenger> PassengerPage = passengerMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<Passenger> passengerList= passengerMapper.selectList(wrapper);
        for (Passenger passenger : passengerList) {
            OrderPassenger orderPassenger=new OrderPassenger();
            orderPassenger.setPassengerName(passenger.getPassengerName());
            orderPassenger.setPassengerIdentity(passenger.getIdentityType());
            orderPassenger.setPassengerType(passenger.getPassengerType());
            orderPassenger.setPhone(passenger.getPhone());
            orderPassenger.setIdentityType(passenger.getIdentityType());
        }
        return Result.success(PassengerPage);
    }
    @PutMapping("/order")
    @Transactional
    public Result<?> saveOrder(@RequestBody OrderPassenger orderPassenger) {
        //更新余票数
        LambdaQueryWrapper<Seat> wrapper1=Wrappers.<Seat>lambdaQuery();
        System.out.println(orderPassenger.getTrainId());
        System.out.println(orderPassenger.getRoomId());
        System.out.println(orderPassenger.getSeatType());
        wrapper1.eq(Seat::getTrainId,orderPassenger.getTrainId());
        wrapper1.eq(Seat::getSeatType,orderPassenger.getSeatType());
        List<Seat> seatList = seatMapper.selectList(wrapper1);
        int i = 0;
        for (; i < seatList.size(); i++) {
            if (seatList.get(i).getSeatSum()>0){
                seatList.get(i).setSeatSum(seatList.get(i).getSeatSum()-1);
                break;
            }
        }
        LambdaQueryWrapper<Seat> wrapper2=Wrappers.<Seat>lambdaQuery();
        wrapper2.eq(Seat::getTrainId,orderPassenger.getTrainId());
        wrapper2.eq(Seat::getRoomId,seatList.get(i).getRoomId());
        Seat seat=seatMapper.selectOne(wrapper2);
        seat.setSeatSum(seat.getSeatSum()-1);
        seatMapper.update(seat,wrapper2);
        Order order=new Order();
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.eq(Order::getPhone,orderPassenger.getPhone())
                .eq(Order::getPassengerIdentity,orderPassenger.getPassengerIdentity())
                .eq(Order::getTrainId,orderPassenger.getTrainId())
                .eq(Order::getTrainStart,orderPassenger.getTrainStart())
                .eq(Order::getTrainEnd,orderPassenger.getTrainEnd())
                .eq(Order::getTrainDate,orderPassenger.getTrainDate())
                .eq(Order::getTrainStarttime,orderPassenger.getTrainStarttime())
                .eq(Order::getTrainEndtime,orderPassenger.getTrainEndtime());
        Order order2 = orderMapper.selectOne(wrapper);
        if (order2!=null){
            //说明已经选过座位了 是要更改座位
            order.setOrderId(order2.getOrderId());
            order.setOrderState("未支付");
            order.setOrderType("成人票");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setOrderTime(df.format(new Date()));
            order.setPhone(orderPassenger.getPhone());
            order.setPassengerName(orderPassenger.getPassengerName());
            order.setPassengerIdentity(orderPassenger.getPassengerIdentity());
            order.setTrainId(orderPassenger.getTrainId());
            order.setRoomId(orderPassenger.getRoomId());
            order.setSeatId(orderPassenger.getSeatId());
            order.setPrice(orderPassenger.getPrice());
            order.setTrainStart(orderPassenger.getTrainStart());
            order.setTrainEnd(orderPassenger.getTrainEnd());
            order.setTrainDate(String.valueOf(orderPassenger.getTrainDate()));
            order.setTrainStarttime(orderPassenger.getTrainStarttime());
            order.setTrainEndtime(orderPassenger.getTrainEndtime());
            orderMapper.updateById(order);
        }else{
            order.setOrderState("未支付");
            order.setOrderType("成人票");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setOrderTime(df.format(new Date()));
            order.setPhone(orderPassenger.getPhone());
            order.setPassengerName(orderPassenger.getPassengerName());
            order.setPassengerIdentity(orderPassenger.getPassengerIdentity());
            order.setTrainId(orderPassenger.getTrainId());
//            order.setRoomId(orderPassenger.getRoomId());
            order.setRoomId(Integer.valueOf(seatList.get(i).getRoomId()));
            order.setSeatId(orderPassenger.getSeatId());
            order.setPrice(orderPassenger.getPrice());
            order.setTrainStart(orderPassenger.getTrainStart());
            order.setTrainEnd(orderPassenger.getTrainEnd());
            order.setTrainDate(String.valueOf(orderPassenger.getTrainDate()));
            order.setTrainStarttime(orderPassenger.getTrainStarttime());
            order.setTrainEndtime(orderPassenger.getTrainEndtime());
            orderMapper.insert(order);
        }

//        if (orderPassenger.getOrderId()==null){//说明是第一次进来
//            System.out.println(order);
//        }else{
//            LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
//            wrapper.eq(Order::getOrderId,orderPassenger.getOrderId());
//            Order order1=orderMapper.selectOne(wrapper);
//            order.setOrderState("未支付");
//            order.setOrderType("成人票");
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            order.setOrderTime(df.format(new Date()));
//            order.setPhone(orderPassenger.getPhone());
//            order.setPassengerName(orderPassenger.getPassengerName());
//            order.setPassengerIdentity(orderPassenger.getPassengerIdentity());
//            order.setTrainId(orderPassenger.getTrainId());
//            order.setRoomId(orderPassenger.getRoomId());
//            order.setSeatId(orderPassenger.getSeatId());
//            order.setPrice(orderPassenger.getPrice());
//            order.setTrainStart(orderPassenger.getTrainStart());
//            order.setTrainEnd(orderPassenger.getTrainEnd());
//            order.setTrainDate(orderPassenger.getTrainDate());
//            order.setTrainStarttime(orderPassenger.getTrainStarttime());
//            order.setTrainEndtime(orderPassenger.getTrainEndtime());
//            orderMapper.updateById(order);
//            System.out.println(order);
//        }
        return Result.success();
    }
}
