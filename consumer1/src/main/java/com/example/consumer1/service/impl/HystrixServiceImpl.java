package com.example.consumer1.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class HystrixServiceImpl {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    private RestTemplate restTemplate;

    //同步调用
    //启用Hystrix并指定回调函数error
    @CacheResult
    @HystrixCommand(fallbackMethod = "error")
    public String openCacheByAnnotation1(String str1) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://HELLO-SERVICE/get5?name={1}", String.class, str1);
        System.out.println("获取的响应："+responseEntity.getBody().toString());
        return responseEntity.getBody();
    }

    //这里的throwable参数可以拿到调用方法的异常对象
    public String error(String t, Throwable throwable) {
        System.out.println("参数："+t);
        System.out.println(throwable.getMessage());
        return "error";
    }

    /**
     * 第一种方法没有使用@CacheKey注解，而是使用这个方法进行生成cacheKey的替换办法
     * 这里有两点要特别注意：
     * 1、这个方法的入参的类型必须与缓存方法的入参类型相同，如果不同被调用会报这个方法找不到的异常
     * 2、这个方法的返回值一定是String类型
     */
    public String getCacheKey(String str1){
        return str1;
    }

    /**
     * 使用注解清除缓存 方式1
     * @CacheRemove 必须指定commandKey才能进行清除指定缓存
     */
    @CacheRemove(commandKey = "commandKey1", cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public void flushCacheByAnnotation1(String str1){
        log.info("请求缓存已清空！");
        //这个@CacheRemove注解直接用在更新方法上效果更好
    }

}
