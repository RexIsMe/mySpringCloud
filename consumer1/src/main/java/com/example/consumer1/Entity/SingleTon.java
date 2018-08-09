package com.example.consumer1.Entity;

/**
 * 懒汉式 单例
 */
public class SingleTon {

    public SingleTon singleTon;

    private SingleTon(){}

    public SingleTon getSingleTon() {

        //为避免每次都进到同步代码块中判断导致性能降低
        if (singleTon == null){
            synchronized(SingleTon.class){
                //用于第一次创建对象且正好有并发时避免创建多个对象
                if (singleTon == null){
                    singleTon = new SingleTon();
                    return singleTon;
                }
            }
        }
        return singleTon;
    }
}
