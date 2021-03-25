package com.wang.service;

import com.wang.entity.Comment;
import com.wang.util.PageResult;

import java.util.Map;

public interface CommentService {
    PageResult<Comment> getList(Map<String, Integer> params);

    Boolean deleteOrCheck(Integer[] ids, int deleteOrCheck);

    Boolean reply(Integer id, String reply);

    boolean add(Comment comment);
}
