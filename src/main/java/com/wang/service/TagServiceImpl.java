package com.wang.service;

import com.wang.dao.TagDao;
import com.wang.entity.Tag;
import com.wang.util.PageQuery;
import com.wang.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagDao tagDao;

    @Override
    public PageResult<Tag> getList(Map<String, Integer> params) {
        /**
         * 1.建立查询工具类，用来作dao层的参数
         */
        PageQuery pageQuery=new PageQuery();
        Integer pageSize=params.get("pageSize");//设置每页10条
        Integer currPage=params.get("currPage");
        Integer start=(currPage-1)*pageSize;//设置从哪一条(的下一条)开始
        pageQuery.setStart(start);
        pageQuery.setPageSize(pageSize);
        /**
         * 2.调用dao层接口查询数据
         */
        List<Tag> tagList=tagDao.getList(pageQuery);
        /**
         * 3.建立返回结果类，用来返回给controller
         */
        PageResult<Tag> pageResult=new PageResult<>();
        pageResult.setList(tagList);
        pageResult.setCurrPage(currPage);
        pageQuery.setIsTotal(1);
        Integer totalCount = tagDao.getList(pageQuery).size();
        pageResult.setTotalCount(totalCount);
        Integer totalPage=totalCount/pageSize;
        if(totalCount%pageSize==0)pageResult.setTotalPage(totalPage);
        else pageResult.setTotalPage(totalPage+1);
        return pageResult;
    }

    @Override
    public boolean delete(Integer[] ids) {
        return tagDao.delete(ids);
    }

    @Override
    public boolean add(String name) {
        Tag tag=new Tag();
        tag.setName(name);
        if(tagDao.get(tag)==null||tagDao.get(tag).isEmpty()) return tagDao.add(tag);
        else return false;
    }

    @Override
    public List<Tag> hotTags() {
        return tagDao.getHot();
    }
}
