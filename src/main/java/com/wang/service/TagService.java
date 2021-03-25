package com.wang.service;

import com.wang.entity.Tag;
import com.wang.util.PageResult;

import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Map;

public interface TagService {
    PageResult<Tag> getList(Map<String, Integer> params);

    boolean delete(Integer[] ids);

    boolean add(String name);

    List<Tag> hotTags();
}
