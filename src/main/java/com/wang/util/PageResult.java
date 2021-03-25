package com.wang.util;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PageResult<T> {
    private List<T> list;//数据
    private Integer currPage;//当前页
    private Integer totalPage;//总页数
    private Integer totalCount;//总记录数
    public PageResult(){
        list=null;
        currPage=null;
        totalCount=null;
        totalPage=null;
    }
}
