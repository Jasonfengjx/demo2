package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerMapper extends BaseMapper<Passenger> {
}
