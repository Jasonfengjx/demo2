package com.example.springboot;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.domain.Statistic;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.OrderMapper;
import com.example.springboot.mapper.TrainDirectMapper;

import com.example.springboot.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Autowired
    TrainDirectMapper trainDirectMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderMapper orderMapper;

    @Test
    public void test() {
        String a = "黑河";
        String b = "通北";
        System.out.println(trainDirectMapper.getDirectTrain(a, b));

        String c="黑河";
        String d="北京";
        System.out.println(trainDirectMapper.getTranserTrain(c, d));
    }
    @Test
    public void TestStatistic(){
        System.out.println(orderMapper.getAll());
        System.out.println(orderMapper.getChange());
        System.out.println(orderMapper.getOver());
        System.out.println(orderMapper.getQuit());
        System.out.println(orderMapper.getUnpaid());
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
        Statistic statistic5 = new Statistic("总共",orderMapper.getAll(),
                nt.format(Double.valueOf(orderMapper.getAll()) /orderMapper.getAll()));
        statisticList.add(statistic5);


        for (Statistic each:statisticList){
            System.out.println(each);
        }
    }


    @Test
    public void testUser() {
//        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
//        String search = "";
//        if (StrUtil.isNotBlank(search)) {
//            wrapper.like(User::getName, search);
//        }
//        System.out.println(userMapper.selectList(wrapper));
//    }
//        System.out.println(trainDirectMapper.getAllUsers());
//        System.out.println(trainDirectMapper.getTD());

    }
}