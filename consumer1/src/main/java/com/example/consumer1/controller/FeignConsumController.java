package com.example.consumer1.controller;

import com.example.consumer1.Entity.Book;
import com.example.consumer1.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("FeignTest")
public class FeignConsumController {
    @Autowired
    FeignService feignService;

    @RequestMapping("/hello")
    public String hello() {
        return feignService.hello();
    }

    @RequestMapping("/hello1")
    public String hello1() {
        return feignService.hello("张三");
    }

    @RequestMapping(value = "/hello2")
    public Book hello2() throws UnsupportedEncodingException {
        Book book = feignService.hello(URLEncoder.encode("三国演义","UTF-8"), URLEncoder.encode("罗贯中","UTF-8"), 33);
        System.out.println(book);
        return book;
    }

    @RequestMapping("/hello3")
    public String hello3() {
        Book book = new Book();
        book.setBookName("红楼梦");
        book.setPrice(Double.valueOf("44"));
        book.setAuthor("曹雪芹");
        return feignService.hello(book);
    }

    @RequestMapping("/hello4")
    public String hello4() {
        return feignService.hello("hello4");
    }

}
