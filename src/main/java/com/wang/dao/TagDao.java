package com.wang.dao;

import com.wang.entity.Tag;
import com.wang.util.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagDao {
    Integer getTotal();

    List<Tag> getList(@Param("pageQuery") PageQuery pageQuery);

    boolean delete(Integer[] ids);

    boolean add(@Param("tag") Tag tag);

    List<Tag> get(@Param("tag")Tag tag);

    List<Tag> getHot();
}
