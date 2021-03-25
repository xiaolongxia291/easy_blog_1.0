package com.wang.service;

import com.wang.entity.Link;
import com.wang.util.PageResult;

import java.util.Map;

public interface LinkService {
    PageResult<Link> getList(Map<String, Integer> params);

    boolean delete(Integer[] ids);

    boolean save(Integer id, String url, String name, String description, Integer rank);
}
