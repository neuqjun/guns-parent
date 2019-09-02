package com.stylefeng.guns.api.modular.film.VO;

import com.stylefeng.guns.api.film.VO.ConditionList;
import com.stylefeng.guns.api.film.VO.ConditionListInfo;

import java.util.ArrayList;

public class ConditionListResult<T> {
    int status;

    public ConditionListInfo getData() {
        return data;
    }

    public void setData(ConditionListInfo data) {
        this.data = data;
    }

    ConditionListInfo data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
