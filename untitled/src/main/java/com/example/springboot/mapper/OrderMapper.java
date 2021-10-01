package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select count(*) from order1")
    public Integer getAll();

    @Select("select count(*) " +
            "from order1 " +
            "where order_state = '完成'")
    public Integer getOver();

    @Select("select count(*)" +
            "from order1 " +
            "where order_state = '退票'")
    public Integer getQuit();

    @Select("select count(*)" +
            "from order1 " +
            "where order_state = '改签'")
    public Integer getChange();

    @Select("select count(*)" +
            "from order1 " +
            "where order_state = '未支付'")
    public Integer getUnpaid();

    @Select("select count(*)" +
            "from order1 " +
            "where order_state = '未出行'")
    public Integer getUnOver();

    @Select("select count(*)" +
            "from order1 " +
            "where order_state = '取消'")
    public Integer getQuxiao();

}
