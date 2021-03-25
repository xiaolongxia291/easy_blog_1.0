package com.wang.dao;

import com.wang.entity.Link;
import com.wang.util.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LinkDao {
    Integer getTotal();

    List<Link> getList(@Param("pageQuery") PageQuery pageQuery);

    boolean delete(Integer[] ids);

    boolean add(@Param("link") Link link);

    boolean update(@Param("link") Link link);
}
