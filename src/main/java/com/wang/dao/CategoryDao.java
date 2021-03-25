package com.wang.dao;

import com.wang.entity.Category;
import com.wang.util.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.File;
import java.util.List;

@Mapper
public interface CategoryDao {
    Integer getTotal();

    List<Category> getList(@Param("pageQuery") PageQuery pageQuery);

    Boolean delete(Integer[] ids);

    boolean add(@Param("name") String name, @Param("img") String img);

    boolean update(@Param("id") Integer id,
                   @Param("name") String name, @Param("img") String img);

    List<Category> getListNoPage();

    Category get(@Param("id")int id);

    boolean updateRank(@Param("id")int id);
}
