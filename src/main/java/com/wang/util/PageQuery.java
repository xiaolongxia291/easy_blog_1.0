package com.wang.util;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class PageQuery {
    private Integer start;//从第几条开始
    private Integer pageSize;//每页条数
    //以下参数用于过滤博客
    private Integer categoryId;//根据分类id
    private Integer tagId;//根据tag的id
    private String keyword;//根据搜索框输入的关键词
    private Integer published;//0表示从管理端查询，1表示从访客端
    //以下参数用于过滤评论
    private Integer blogId;//根据博客id过滤出评论
    private Integer status;//0表示从管理端查询，1表示从访客端
    //此参数用于统计筛选后的总数据条数
    private Integer isTotal;

    public PageQuery() {
        categoryId=null;
        tagId=null;
        keyword=null;
        blogId=null;
        status=null;
        published=null;
        isTotal=null;
    }
}
