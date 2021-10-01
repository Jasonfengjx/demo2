package com.example.springboot.service;

import com.example.springboot.domain.Order;
import com.example.springboot.domain.OrderPassenger;
import org.springframework.stereotype.Service;

@Service
public interface TicketService {
//    售票功能在信息发过来的时候去改变数据库中的票数目
//    本质上是个Update
    public Integer minusTicket(OrderPassenger orderPassenger);

//    退票功能，有一个新的数据库专门承装退票的车ID、车厢ID和车票ID
//    这里接收的一个订单类，因为订单类有上述的数据
//    把那个数字再加回去
    public Integer lcancellation(Order order);
}
