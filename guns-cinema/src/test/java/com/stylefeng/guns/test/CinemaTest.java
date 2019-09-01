package com.stylefeng.guns.test;


import com.stylefeng.guns.api.GunsCinemaApplication;
import com.stylefeng.guns.api.cinema.service.CinemaService;
import com.stylefeng.guns.api.cinema.vo.DataVo;
import com.stylefeng.guns.api.cinema.vo.FieldDataVo;
import com.stylefeng.guns.api.modular.cinema.CinemaServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GunsCinemaApplication.class, CinemaServiceImpl.class})
public class CinemaTest {

    @Autowired
    private CinemaService cinemaService;

    @Test
    public void Test1(){
        DataVo dataInfo = cinemaService.getDataInfo("2", 2);
        System.out.println(dataInfo);
    }

    @Test
    public void Test2(){
        FieldDataVo fieldDataVo = cinemaService.getFieldInfo("2");
        System.out.println(fieldDataVo);
    }

}
