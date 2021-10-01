package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.config.Result;
import com.example.springboot.config.ShiroUtil;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    private static final transient Logger log= LoggerFactory.getLogger(UserController.class);
    @PostMapping
    @Transactional
    public Result<?> save(@RequestBody User user){
        if(user.getPassword()==null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @PutMapping
    @Transactional
    public Result<?> update(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper=Wrappers.<User>lambdaQuery();
        wrapper.eq(User::getPhone,user.getPhone());
        User user1=userMapper.selectOne(wrapper);

        if (user1==null){
            return save(user);
        }else{
            userMapper.updateById(user);
            return Result.success();
        }
    }
    @DeleteMapping("/{phone}")
    @Transactional
    public Result<?> delete(@PathVariable String phone){

        userMapper.deleteById(phone);
        return Result.success();

    }
    @GetMapping
    @Transactional
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        //下边必须用一个你名列，可能是修改了吧哈哈哈哈妈了个巴子的
        LambdaQueryWrapper<User> wrapper=Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getName,search);
        }
       Page<User> userPage= userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);

    }
    @PostMapping("/login")
    @Transactional
    public Result<?> login(@RequestBody User user)  {
        Subject subject= SecurityUtils.getSubject();
        String salt=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone,user.getPhone())).getSalt();
        String tokenPass=String.valueOf(ShiroUtil.salt(user.getPassword(),salt));
        UsernamePasswordToken token= new UsernamePasswordToken(user.getPhone(),tokenPass);
        Result<User> result=new Result<User>();
        result.setCode("0");
        result.setMsg("成功");
        try{
            subject.login(token);
            result.setData(userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone,user.getPhone())));
            return result;
        }catch (UnknownAccountException uae) {
            log.info("There is no user with username of " + token.getPrincipal());

            result.setCode("1");
            result.setMsg("失败");
            return result;
        } catch (IncorrectCredentialsException ice) {
            log.info("Password for account " + token.getPrincipal() + " was incorrect!");

            result.setCode("1");
            result.setMsg("失败");
            return result;
        } catch (LockedAccountException lae) {
            log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
            result.setCode("1");
            result.setMsg("失败");
            return result;
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?
            result.setCode("1");
            result.setMsg("失败");
            return result;
            // error?
        }
    }
    @PostMapping("/register")
    @Transactional
    public Result<?> register(@RequestBody User user)  {
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone,user.getPhone()));
        if(res!=null){
          return Result.error("-1","该手机号已经被注册");
        }else{
            if (user.getPassword()==null){
                user.setPassword("123456");
            }
            if (user.getRole()==null)
            {
                user.setRole(2);
            }
            if (user.getName()==null)
            {
                user.setName("用户");
            }
        }
        String salt= ShiroUtil.createSalt();
        String pass=ShiroUtil.salt(user.getPassword(),salt);
        user.setSalt(salt);
        user.setPassword(pass);
        userMapper.insert(user);
        return Result.success();
    }
    @PostMapping("/logout")
    @Transactional
    public Result<?> logout(@RequestBody User user)  {
        //退出方法，可能要集成安全框架
        return Result.success();
    }
    @GetMapping("/{phone}")
    @Transactional
    public Result<?> getUserByPhone(@PathVariable String phone){
        LambdaQueryWrapper<User> wrapper=Wrappers.<User>lambdaQuery();
        wrapper.eq(User::getPhone,phone);
        User user=userMapper.selectOne(wrapper);
        return Result.success(user);

    }
}
