package com.example.consumer1.tests;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class httpTest {

    @Test
    public void test(){



    }

    public static void main(String[] args) throws Exception{

//        batchFlieUpload();

    }


    public static void batchFlieUpload() throws Exception{

        HttpClient httpclient = new DefaultHttpClient();
        try {
            //新建一个httpclient Post 请求
            HttpPost httppost = new
                    HttpPost("http://localhost:8060/api/intellif/monitor/person/zip/upload/1.0");
            //由于只是测试使用 这里的路径对应本地文件的物理路径
            String filePath = "C:\\Users\\Administrator\\Desktop\\20180723.zip";
            FileBody bin = new FileBody(new File(filePath));
            File myfile = new File(filePath);
            long size  = myfile.length();
            //向MultipartEntity添加必要的数据
            StringBody comment = new StringBody("1.doc", Charset.forName("UTF-8"));
            httppost.setHeader("ContentType","application/json");

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file",bin);//file为请求后台的Fileupload参数
            reqEntity.addPart("filename",comment);//请求后台Fileupload的参数

            Map<String, String> params = new HashMap<>();
            params.put("paramValues","bankId=1,serviceType=2,extendLongField1=3,extendStringField1=4");
            params.put("paramRule","imageName3:name3,birthday:name11,descritpion:name18,extendLongField:name15,address:name6,status:name8,gender:name4,type:name14,ruleId:name5,sourceId:name9,mSwitch:name10,imageName1:name1,phone:name12,extendStringField:name13,nation:name7,name:name0,cidType:name16,cidValue:name17,imageName2:name2,extendLongField2:name19,extendStringField1:name20");
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    StringBody contentBody = new StringBody(entry.getValue(),
                            "text/plain", Charset.forName("UTF-8"));
                    reqEntity.addPart(entry.getKey(), contentBody);
                }
            }
            httppost.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == HttpStatus.SC_OK){
                System.out.println("服务器正常响应.....");
                HttpEntity resEntity = response.getEntity();
                System.out.println(
                        //httpclient自带的工具类读取返回数据
                        EntityUtils.toString(resEntity));
                System.out.println( resEntity.getContent());
                EntityUtils.consume( resEntity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }

    }


}
