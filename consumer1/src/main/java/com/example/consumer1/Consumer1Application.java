package com.example.consumer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//启用断路器（熔断器）
@EnableCircuitBreaker
//启用服务的注册与发现
@EnableDiscoveryClient
//开启Spring Cloud Feign
@EnableFeignClients
@SpringBootApplication
public class Consumer1Application {

    public static void main(String[] args) {
        SpringApplication.run(Consumer1Application.class, args);
    }

    /**
     * RestTemplate可以帮助我们发起一个GET或者POST请求
     * 在提供Bean的同时，添加@LoadBalanced注解，表示开启客户端负载均衡
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
