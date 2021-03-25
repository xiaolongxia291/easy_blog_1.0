package com.wang.controller;

import com.wang.entity.Blog;
import com.wang.service.AdminService;
import com.wang.service.BlogService;
import com.wang.service.CategoryService;
import com.wang.util.MarkDownUtil;
import com.wang.util.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Api(tags="博客模块")
@Transactional
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private AdminService adminService;

    @ApiOperation("进入博客管理模块")
    @GetMapping("/admin/blog")
    public String page(HttpServletRequest request) {
        request.setAttribute("path", "blog");
        return "admin/blog";
    }

    /**
     *查询博客列表
     */
    @ApiOperation("管理端查询博客列表")
    @GetMapping("/admin/blog/list")
    @ResponseBody
    public PageResult listAdmin(@RequestParam(value = "currPage")Integer currPage,
                                @RequestParam(value = "pageSize")Integer pageSize,
                                @RequestParam(value = "keyword",required = false)String keyword) {
        Map<String,Object> params = new HashMap<>();
        if(!StringUtils.isEmpty(keyword))params.put("keyword",keyword);
        params.put("currPage",currPage);
        params.put("pageSize",pageSize);
        params.put("categoryId",0);
        params.put("tagId",0);
        return blogService.getList(params);
    }


    /**
     * 删除博客
     */
    @ApiOperation("管理端批量删除博客")
    @PostMapping("/admin/blog/delete")
    @ResponseBody
    public String delete(@RequestBody Integer[] ids){
        if(blogService.delete(ids))return "success";
        else return "删除失败";
    }

    /**
     * 编辑器
     */
    @ApiOperation("进入修改、发布博客模块")
    @GetMapping("/admin/edit")
    public String update(HttpServletRequest request,
                         @RequestParam(value="id",required = false)Integer id) {
        request.setAttribute("path", "edit");
        request.setAttribute("allCategory",categoryService.getListNoPage());
        if(id==null||id>0) {
            request.setAttribute("blog",blogService.getBlogById(id));
        }
        return "admin/edit";
    }

    /**
     * 保存博客
     */
    @ApiOperation("新增、修改博客")
    @PostMapping("/admin/blog/save")
    @ResponseBody
    public String save(@RequestParam(value="id",required = false)Integer id,
                       @RequestParam(value="categoryId")Integer categoryId,
                       @RequestParam(value="title")String title,
                       @RequestParam(value="tags")String tags,
                       @RequestParam(value="content")String content,
                       @RequestParam(value="img")String img,
                       @RequestParam(value="status")Byte status) {
        Blog blog=new Blog(id,title,img,content,categoryId,null,status,
                null,null,null,null,tags);
        if(blogService.save(blog)) return "success";
        else return "提交失败";
    }
}
