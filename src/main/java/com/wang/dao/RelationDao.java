package com.wang.dao;

import com.wang.entity.Relation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RelationDao {
    boolean deleteByBlogId(Integer[] ids);

    boolean add(@Param("tagId") Integer tagId, @Param("blogId") Integer blogId);

    List<Relation> get(@Param("relation") Relation relation);
}
