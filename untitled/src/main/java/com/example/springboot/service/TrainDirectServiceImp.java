package com.example.springboot.service;

import com.example.springboot.domain.TrainTransfer;
import com.example.springboot.domain.TranDirect;
import com.example.springboot.mapper.TrainDirectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public class TrainDirectServiceImp implements TrainDirectService{

    @Autowired
    static TrainDirectMapper trainDirectMapper;



}
