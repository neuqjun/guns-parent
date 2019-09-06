package com.stylefeng.guns.api.util;

import java.util.Date;

public class DateUtil {

    public static Date getAfterMonthDate(Integer value) {
        return new Date(System.currentTimeMillis() + value * 31 * 24 * 3600);
    }
}
