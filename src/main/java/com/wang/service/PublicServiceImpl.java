package com.wang.service;

import com.wang.dao.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class PublicServiceImpl implements PublicService {
    @Resource
    private BlogDao blogDao;
    @Resource
    private CategoryDao categoryDao;
    @Resource
    private CommentDao commentDao;
    @Resource
    private LinkDao linkDao;
    @Resource
    private TagDao tagDao;


    @Override
    public Map<String, Integer> getAllCount() {
        Map<String,Integer> counts=new HashMap<>();
        counts.put("categoryCount",categoryDao.getTotal());
        counts.put("commentCount",commentDao.getTotal());
        counts.put("linkCount",linkDao.getTotal());
        counts.put("tagCount",tagDao.getTotal());
        counts.put("blogCount",blogDao.getTotal());
        return counts;
    }
}
