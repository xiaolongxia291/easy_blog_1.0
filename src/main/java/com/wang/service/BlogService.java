package com.wang.service;


import com.wang.entity.Blog;
import com.wang.util.PageResult;

import java.util.List;
import java.util.Map;

public interface BlogService {

    PageResult<Blog> getList(Map<String, Object> params);

    Boolean delete(Integer[] ids);

    Blog getBlogById(Integer id);

    boolean save(Blog blog);

    List<Blog> newBlogs();

    List<Blog> hotBlogs();

    boolean updateViews(Integer id);
}
