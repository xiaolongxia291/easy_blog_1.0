package com.wang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Relation {
    private Integer id;
    private Integer blogId;
    private Integer tagId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    public Relation() {
        id=null;
        blogId=null;
        tagId=null;
        addTime=null;
    }
}
