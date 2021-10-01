package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Station;
import com.example.springboot.entity.Train;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainMapper extends BaseMapper<Train> {
}
