package com.wang.dao;

import com.wang.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminDao {
    Admin getAdmin(@Param("admin") Admin admin);

    Boolean updateAdmin(@Param("admin")Admin admin);
}
