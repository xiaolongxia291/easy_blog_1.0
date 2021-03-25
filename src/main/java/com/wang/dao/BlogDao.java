package com.wang.dao;

import com.wang.entity.Blog;
import com.wang.util.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogDao {
    Integer getTotal();

    List<Blog> getList(@Param("pageQuery") PageQuery pageQuery);

    Boolean delete(Integer[] ids);

    Blog getBlog(@Param("id") Integer id);

    boolean add(@Param("blog") Blog blog);

    boolean update(@Param("blog")Blog blog);

    List<Blog> getSeveral(@Param("newOrHot") int newOrHot);

    boolean updateViews(@Param("id") Integer id);
}
