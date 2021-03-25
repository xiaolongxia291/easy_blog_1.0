package com.wang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Blog {
    private Integer id;
    private String title;
    private String img;
    private String content;
    private Integer categoryId;
    private String categoryName;
    private Byte status;//0草稿，1发布
    private Integer views;//访问量
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;
    private Byte deleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private String tags;

}
