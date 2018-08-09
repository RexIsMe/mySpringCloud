package com.example.consumer1.controller;

import com.example.consumer1.Entity.Book;
import com.example.consumer1.service.FeignService;
import com.example.consumer1.service.impl.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class ConsumerController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    FeignService feignService;
    @Autowired
    HelloService helloService;
    @RequestMapping(value = "/ribbon-consumer",method = RequestMethod.GET)
    public String helloController() {
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
    }


//    @RequestMapping(value = "/ribbon-consumer",method = RequestMethod.GET)
//    public String helloController() throws ExecutionException, InterruptedException {
//        return helloService.hello("你的名字", "rex");
////        Future<String> fs = helloService.AsyncHello();
////        return fs.get().toString();
//    }

    //声明式服务调用Feign
    @RequestMapping(value = "/feignHello",method = RequestMethod.GET)
    public String feignHello() throws ExecutionException, InterruptedException {
        return feignService.hello();
    }


    /**
     * ------------------------------------------------------------Hystrix的缓存使用------------------------------------------------------------------
     */
    @RequestMapping(value = "/hystrix_cache1",method = RequestMethod.GET)
    public String hystrixCache1(){
        return helloService.hystrixCache("你的名字", "rex");
    }



    /**
     * ------------------------------------------------------------联系使用RestTemplate------------------------------------------------------------------
     */
    //GET请求：
    @RequestMapping(value = "/restTemplate_get1",method = RequestMethod.GET)
    public String getRequestTest1(){
        ResponseEntity<String> re = restTemplate.getForEntity("http://HELLO-SERVICE/test1", String.class);
        return re.getHeaders().toString()+re.getStatusCode()+re.getStatusCodeValue()+re.getBody();
    }

    @RequestMapping(value = "/restTemplate_get2",method = RequestMethod.GET)
    public String getRequestTest2(){
        ResponseEntity<Book> re = restTemplate.getForEntity("http://HELLO-SERVICE/get2", Book.class);
        return re.getHeaders().toString()+re.getStatusCode()+re.getStatusCodeValue()+re.getBody();
    }

    @RequestMapping(value = "/restTemplate_get3",method = RequestMethod.GET)
    public String getRequestTest3(){
        return restTemplate.getForObject("http://HELLO-SERVICE/get3", Book.class).toString();
    }

    @RequestMapping(value = "/restTemplate_get4",method = RequestMethod.GET)
    public String getRequestTest4(){
        ResponseEntity<Book> re = restTemplate.getForEntity("http://HELLO-SERVICE/get4?param1={1}&param2={2}", Book.class, "你的名字","rex");
        return re.getHeaders().toString()+re.getStatusCode()+re.getStatusCodeValue()+re.getBody();
    }

    @RequestMapping(value = "/restTemplate_get5",method = RequestMethod.GET)
    public String getRequestTest5(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("1","你的名字");
        map.put("2","lzq");
        ResponseEntity<Book> re = restTemplate.getForEntity("http://HELLO-SERVICE/get4?param1={1}&param2={2}", Book.class,map);
        return re.getHeaders().toString()+re.getStatusCode()+re.getStatusCodeValue()+re.getBody();
    }

    @RequestMapping(value = "/restTemplate_get6",method = RequestMethod.GET)
    public String getRequestTest6(){
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://HELLO-SERVICE/get5?name={name}").build().expand("王五").encode();
        URI uri = uriComponents.toUri();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        return responseEntity.getBody();
    }

}
