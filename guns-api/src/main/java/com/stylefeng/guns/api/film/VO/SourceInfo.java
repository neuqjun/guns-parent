package com.stylefeng.guns.api.film.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class SourceInfo implements Serializable {
    Integer sourceId;
    String sourceName;
    Boolean active = false;

    public SourceInfo(Integer sourceId, String sourceName) {
        this.sourceId = sourceId;
        this.sourceName = sourceName;
    }
}
