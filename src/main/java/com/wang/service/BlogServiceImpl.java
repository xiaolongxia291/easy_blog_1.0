package com.wang.service;

import com.wang.dao.BlogDao;
import com.wang.dao.CategoryDao;
import com.wang.dao.RelationDao;
import com.wang.dao.TagDao;
import com.wang.entity.Blog;
import com.wang.entity.Relation;
import com.wang.entity.Tag;
import com.wang.util.MarkDownUtil;
import com.wang.util.PageQuery;
import com.wang.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogDao blogDao;
    @Resource
    private RelationDao relationDao;
    @Resource
    private CategoryDao categoryDao;
    @Resource
    private TagDao tagDao;

    @Override
    public PageResult<Blog> getList(Map<String, Object> params) {
        /**
         * 1.建立查询工具类，用来作dao层的参数
         */
        PageQuery pageQuery=new PageQuery();
        Integer pageSize=(Integer) params.get("pageSize");//设置每页条数
        Integer currPage=(Integer)params.get("currPage");
        Integer start=(currPage-1)*pageSize;//设置从哪一条开始
        pageQuery.setStart(start);
        pageQuery.setPageSize(pageSize);
        //处理需要用于筛选的条件
        //没有筛选参数则默认查询所有；按分类查询；按tag查询；按搜索框关键词查询
        Integer categoryId=(Integer)params.get("categoryId");
        if(categoryId!=null&&categoryId>0)pageQuery.setCategoryId(categoryId);
        Integer tagId=(Integer)params.get("tagId");
        if(tagId!=null&&tagId>0)pageQuery.setTagId(tagId);
        if(params.get("keyword")!=null)pageQuery.setKeyword((String)params.get("keyword"));
        if(params.get("published")!=null)pageQuery.setPublished(1);
        /**
         * 2.调用dao层接口查询数据
         */
        List<Blog> blogList=blogDao.getList(pageQuery);
        /**
         * 3.建立返回结果类，用来返回给controller
         */
        PageResult<Blog> pageResult=new PageResult<>();
        pageResult.setList(blogList);
        pageResult.setCurrPage(currPage);
        pageQuery.setIsTotal(1);
        Integer totalCount = blogDao.getList(pageQuery).size();
        pageResult.setTotalCount(totalCount);
        Integer totalPage=totalCount/pageSize;
        if(totalCount%pageSize==0)pageResult.setTotalPage(totalPage);
        else pageResult.setTotalPage(totalPage+1);
        return pageResult;
    }

    @Override
    public Boolean delete(Integer[] ids) {
        return blogDao.delete(ids)&&relationDao.deleteByBlogId(ids);
    }

    @Override
    public Blog getBlogById(Integer id) {
        return blogDao.getBlog(id);
    }

    @Override
    public boolean save(Blog blog) {
        int newCategoryId=blog.getCategoryId();
        String newCategoryName=categoryDao.get(newCategoryId).getName();
        blog.setCategoryName(newCategoryName);//不管什么情况。博客名都需要设置
        //1新增博客:处理分类的rank
        if(blog.getId()==0) {
            blogDao.add(blog);
            categoryDao.updateRank(newCategoryId);
        }
        //2修改博客：若变更分类，额外需要处理分类的rank。不变更分类，只需要修改博客
        else{
            int oldCategoryId=blogDao.getBlog(blog.getId()).getCategoryId();
           if(newCategoryId!=oldCategoryId){
               categoryDao.updateRank(newCategoryId);
           }
           blogDao.update(blog);
        }
        //处理tag，看哪些是新的tag需要新增tag
        //对每一个tag(只有是没有过记录的)都需要新增tag-blog数据
        String[] tagNames = blog.getTags().split(",");
        ArrayList<Integer> allId=new ArrayList<>();
        for(String tagName:tagNames){
            List<Tag> tag=tagDao.get(new Tag(null,tagName,null,null));
            if(tag!=null&&!tag.isEmpty())allId.add(tag.get(0).getId());
            else{
                Tag tempTag=new Tag(null,tagName,null,null);
                tagDao.add(tempTag);
                allId.add(tempTag.getId());
            }
        }
        for(Integer id:allId){
            Relation relation=new Relation(null,blog.getId(),id,null);
            List<Relation> record=relationDao.get(relation);
            if(record==null||record.isEmpty()) relationDao.add(id,blog.getId());
        }
        return true;
    }

    @Override
    public List<Blog> newBlogs() {
        return blogDao.getSeveral(0);
    }

    @Override
    public List<Blog> hotBlogs() {
        return blogDao.getSeveral(1);
    }

    @Override
    public boolean updateViews(Integer id) {
        return blogDao.updateViews(id);
    }
}
