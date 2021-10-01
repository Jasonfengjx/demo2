package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("user")
//实体类注解
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User  {
    @TableId
    private String phone;
    private String name;
    private String password;
    private String address;
    private String sex;
    private Integer role;
    private String email;
    private String identity;
    private Integer age;
    private String salt;
}
