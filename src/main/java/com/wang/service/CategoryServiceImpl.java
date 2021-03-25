package com.wang.service;

import com.wang.dao.CategoryDao;
import com.wang.entity.Category;
import com.wang.util.PageQuery;
import com.wang.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryDao categoryDao;

    @Override
    public PageResult<Category> getList(Map<String, Integer> params) {
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
        List<Category> categoryList=categoryDao.getList(pageQuery);
        /**
         * 3.建立返回结果类，用来返回给controller
         */
        PageResult<Category> pageResult=new PageResult<>();
        pageResult.setList(categoryList);
        pageResult.setCurrPage(currPage);
        pageQuery.setIsTotal(1);
        Integer totalCount = categoryDao.getList(pageQuery).size();
        pageResult.setTotalCount(totalCount);
        Integer totalPage=totalCount/pageSize;
        if(totalCount%pageSize==0)pageResult.setTotalPage(totalPage);
        else pageResult.setTotalPage(totalPage+1);
        return pageResult;
    }

    @Override
    public Boolean delete(Integer[] ids) {
        return categoryDao.delete(ids);
    }

    @Override
    public boolean save(int categoryId,String name, String img) {
        if(categoryId==0) return categoryDao.add(name,img);
        else return categoryDao.update(categoryId,name,img);
    }

    @Override
    public List<Category> getListNoPage() {
        return categoryDao.getListNoPage();
    }
}
