package com.example.consumer1.controller;

import com.example.consumer1.service.impl.HystrixServiceImpl;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class HystrixController {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    HystrixServiceImpl hystrixService;

    /**
     * 注解方式请求缓存，第一种
     */
    @GetMapping("/cacheAnnotation1")
    public String openCacheByAnnotation1(){
        //初始化Hystrix请求上下文
        HystrixRequestContext.initializeContext();
        //访问并开启缓存
        String str1 = "1";
        String result1 = hystrixService.openCacheByAnnotation1(str1);
        String result2 = hystrixService.openCacheByAnnotation1(str1);
        log.info("first request result is:{"+result1+"} ,and secend request result is: {"+result2+"}");
        //清除缓存
        hystrixService.flushCacheByAnnotation1(str1);
        //再一次访问并开启缓存
        String result3 = hystrixService.openCacheByAnnotation1(str1);
        String result4 = hystrixService.openCacheByAnnotation1(str1);
        log.info("first request result is:{"+result3+"} ,and secend request result is: {"+result4+"}");

        return result1+"\n"+result2+"\n"+result3+"\n"+result4;
    }

}
