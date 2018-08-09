package com.example.consumer1.service.impl;

import com.example.consumer1.Entity.Book;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Component
public class HelloService {

    static {
        HystrixRequestContext.initializeContext();
        System.out.println("没有进来吗...");
    }

    @Autowired
    private RestTemplate restTemplate;

    @CacheResult
    @HystrixCommand(fallbackMethod = "error")
    public String hystrixCache(@CacheKey String... params){
        Map<String, String> map = null;
        if(params != null){
            map = new HashMap<String, String>();
            for (int i = 0; i < params.length; i++){
                map.put("param"+(i++), params[i]);
            }
        }
        ResponseEntity<Book> re = restTemplate.getForEntity("http://HELLO-SERVICE/get4?param1={1}&param2={2}", Book.class, map);
        return re.getBody().toString();
    }


    //同步调用
    //启用Hystrix并指定回调函数error
    @CacheResult
    @HystrixCommand(fallbackMethod = "error")
    public String hello(String str1, String str2) {
        try{
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://HELLO-SERVICE/get4?param1={1}&param2={2}", String.class, str1, str2);
            System.out.println("获取的响应："+responseEntity.getBody().toString());
            return responseEntity.getBody();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return "fail";
    }



    //异步调用
    @HystrixCommand(fallbackMethod = "error")
    public Future<String> AsyncHello(){
            return new AsyncResult<String>() {
            @Override
            public String invoke() {
//                int i = 1/0;
                return restTemplate.getForObject("http://HELLO-SERVICE/hello", String.class);
            }
        };
    }


    //这里的throwable参数可以拿到调用方法的异常对象
    public String error(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return "error";
    }

    //这里的throwable参数可以拿到调用方法的异常对象
    public String error(String t,String s,Throwable throwable) {
        System.out.println("参数："+t+","+s);
        System.out.println(throwable.getMessage());
        return "error";
    }

}
