package com.wang.service;

import com.wang.dao.CommentDao;
import com.wang.entity.Comment;
import com.wang.util.PageQuery;
import com.wang.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;

    @Override
    public PageResult<Comment> getList(Map<String, Integer> params) {
        /**
         * 1.建立查询工具类，用来作dao层的参数
         */
        PageQuery pageQuery=new PageQuery();
        Integer pageSize=params.get("pageSize");//设置每页10条
        Integer currPage=params.get("currPage");
        Integer start=(currPage-1)*pageSize;//设置从哪一条(的下一条)开始
        pageQuery.setStart(start);
        pageQuery.setPageSize(pageSize);
        //处理需要用于筛选的条件,没有筛选参数则默认查询所有
        Integer blogId=params.get("blogId");
        if(blogId!=null&&blogId>0)pageQuery.setBlogId(blogId);
        Integer status=params.get("status");
        if(status!=null)pageQuery.setStatus(1);
        /**
         * 2.调用dao层接口查询数据
         */
        List<Comment> commentList=commentDao.getList(pageQuery);
        /**
         * 3.建立返回结果类，用来返回给controller
         */
        PageResult<Comment> pageResult=new PageResult<>();
        pageResult.setList(commentList);
        pageResult.setCurrPage(currPage);
        pageQuery.setIsTotal(1);
        Integer totalCount = commentDao.getList(pageQuery).size();
        pageResult.setTotalCount(totalCount);
        Integer totalPage=totalCount/pageSize;
        if(totalCount%pageSize==0)pageResult.setTotalPage(totalPage);
        else pageResult.setTotalPage(totalPage+1);
        return pageResult;
    }

    @Override
    public Boolean deleteOrCheck(Integer[] ids, int deleteOrCheck) {
        return commentDao.deleteOrCheck(ids,deleteOrCheck);
    }

    @Override
    public Boolean reply(Integer id, String reply) {
        return commentDao.reply(id,reply);
    }

    @Override
    public boolean add(Comment comment) {
        return commentDao.add(comment);
    }
}
