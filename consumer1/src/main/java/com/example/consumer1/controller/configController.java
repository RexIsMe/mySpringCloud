//package com.example.consumer1.controller;
//
//import org.omg.CORBA.Environment;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RefreshScope
//public class configController {
//
//    @Value("${sang}")
//    String sang;
////    @Autowired
////    Environment env;
//
//    @RequestMapping("/sang")
//    public String sang() {
//        return this.sang;
//    }
////    @RequestMapping("/sang2")
////    public String sang2() {
////        return env.getProperty("sang", "未定义");
////    }
//
//}
