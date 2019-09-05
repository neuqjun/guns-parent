package com.stylefeng.guns.api.cinema.vo;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
@Data
public class CinemaListVo implements Serializable {

    private static final long serialVersionUID = -7726580500623536875L;

    private Integer status;

    private String imgPre;

    private String msg;

    private Integer nowPage;

    private Long totalPage;

    private List<CinemaInfo> data;
}
