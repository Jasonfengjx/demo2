package com.example.springboot.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("mana") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
//        Map<String,String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
//        filterMap.put("admin/*","perms[admin]");
//        filterMap.put("/user/*","authc");
//        bean.setFilterChainDefinitionMap(filterMap);
//        bean.setLoginUrl("/login");
//        bean.setUnauthorizedUrl("/noauth");
/**
 * anon:无需认证就可以访问
 * authc：必须认证了才能访问
 * user：必须拥有记住我功能才能使用
 * perms：拥有对某个资源的权限才能访问
 * role：拥有某个角色权限才能访问
 * */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user", "authc");
        filterMap.put("/order", "authc");
        filterMap.put("/passenger", "authc");
        filterMap.put("/seat", "authc");
        filterMap.put("/station", "authc");
        filterMap.put("/train", "authc");
        filterMap.put("/ts", "authc");
        filterMap.put("/selfinfo", "authc");
        filterMap.put("/trainsearch", "authc");
        filterMap.put("/trainInfo", "authc");
        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    //DefaultWebSecurityManger
    @Bean("mana")
    public DefaultWebSecurityManager getdefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建Realm 对象 需要自定义
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

}
