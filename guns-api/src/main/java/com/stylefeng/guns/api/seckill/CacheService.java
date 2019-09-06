package com.stylefeng.guns.api.seckill;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class CacheService {

    private Cache cache;


    //初始化cache
    @PostConstruct
    public void  init(){
        cache = CacheBuilder.newBuilder()

                //设置cache初始容量值
                .initialCapacity(10)
                //设置最大容量值
                .maximumSize(20)
                //设置过期时间
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }
    //存放数据
    public void setCacheInlocal(String key,Object object) {
        cache.put(key,object);
    }

    //取数据
    public Object getCacheInlocal(String key) {
        Object object = cache.getIfPresent(key);
        return object;
    }
}
