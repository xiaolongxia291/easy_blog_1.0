package com.wang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Tag {
    private Integer id;
    private String name;
    private Byte deleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    public Tag() {
        id=null;
        name=null;
        deleted=null;
        addTime=null;
    }
}
