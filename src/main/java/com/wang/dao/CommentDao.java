package com.wang.dao;

import com.wang.entity.Comment;
import com.wang.util.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {
    Integer getTotal();

    List<Comment> getList(@Param("pageQuery") PageQuery pageQuery);

    Boolean deleteOrCheck(Integer[] ids, @Param("deleteOrCheck") int deleteOrCheck);

    Boolean reply(@Param("id")Integer id, @Param("reply")String reply);

    boolean add(@Param("comment") Comment comment);
}
