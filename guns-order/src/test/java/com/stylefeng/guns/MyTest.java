package com.stylefeng.guns;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.io.*;
@RunWith(SpringRunner.class)
public class MyTest {
    @Test
    public void test(){
        InputStream is = null;
        try {
            is = new FileInputStream("seats/123214.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while (true){
            try {
                if (!((line = in.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.append(line);
        }
        JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
        String ids = jsonObject.getString("ids");
        Jedis jedis = new Jedis();
        //jedis.set("seats/123214.json",ids);
        String s = jedis.get("seats/123214.json");
        System.out.println(s);
        return ;
    }
}
