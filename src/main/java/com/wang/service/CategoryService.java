package com.wang.service;

import com.wang.entity.Category;
import com.wang.util.PageResult;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    PageResult<Category> getList(Map<String, Integer> params);

    Boolean delete(Integer[] ids);

    boolean save(int categoryId, String name, String img);

    List<Category> getListNoPage();
}
