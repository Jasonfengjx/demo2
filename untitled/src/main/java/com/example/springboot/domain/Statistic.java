package com.example.springboot.domain;

import jdk.nashorn.internal.ir.IdentNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistic {
    private String type;
    private Integer number;
    private String ratio;
}
