package com.wang.service;

import com.wang.dao.LinkDao;
import com.wang.entity.Link;
import com.wang.util.PageQuery;
import com.wang.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class LinkServiceImpl implements LinkService {
    @Resource
    private LinkDao linkDao;

    @Override
    public PageResult<Link> getList(Map<String, Integer> params) {
        /**
         * 1.建立查询工具类，用来作dao层的参数
         */
        PageQuery pageQuery=new PageQuery();
        Integer pageSize=params.get("pageSize");
        Integer currPage=params.get("currPage");
        Integer start=(currPage-1)*pageSize;//设置从哪一条(的下一条)开始
        pageQuery.setStart(start);
        pageQuery.setPageSize(pageSize);
        /**
         * 2.调用dao层接口查询数据
         */
        List<Link> linkList=linkDao.getList(pageQuery);
        /**
         * 3.建立返回结果类，用来返回给controller
         */
        PageResult<Link> pageResult=new PageResult<>();
        pageResult.setList(linkList);
        pageResult.setCurrPage(currPage);
        pageQuery.setIsTotal(1);
        Integer totalCount = linkDao.getList(pageQuery).size();
        pageResult.setTotalCount(totalCount);
        Integer totalPage=totalCount/pageSize;
        if(totalCount%pageSize==0)pageResult.setTotalPage(totalPage);
        else pageResult.setTotalPage(totalPage+1);
        return pageResult;
    }

    @Override
    public boolean delete(Integer[] ids) {
        return linkDao.delete(ids);
    }

    @Override
    public boolean save(Integer id, String url, String name, String description, Integer rank) {
        Link link=new Link();
        link.setId(id);
        link.setDescription(description);
        link.setName(name);
        link.setRank(rank);
        link.setUrl(url);
        //新增
        if(id==0){
            return linkDao.add(link);
        }
        else return linkDao.update(link);
    }
}
