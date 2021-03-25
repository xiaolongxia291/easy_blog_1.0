package com.wang.controller;

import com.wang.entity.Blog;
import com.wang.entity.Comment;
import com.wang.service.*;
import com.wang.util.MarkDownUtil;
import com.wang.util.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@Api(tags="访客端")
@RequestMapping("/visit")
public class VisitController {
    @Resource
    private AdminService adminService;
    @Resource
    private BlogService blogService;
    @Resource
    private TagService tagService;
    @Resource
    private CommentService commentService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private LinkService linkService;

    /**
     * 首页，博客列表
     */
    @GetMapping({"/",""})
    @ApiOperation("进入首页")
    public String index(HttpServletRequest request) {
        return this.indexPage(request, 1);
    }

    @ApiOperation("查询首页数据，显示博客列表")
    @GetMapping({"/index"})
    public String indexPage(HttpServletRequest request,
                            @RequestParam(value = "currPage") Integer currPage) {
        Map<String,Object> params = new HashMap<>();
        params.put("currPage",currPage);
        params.put("pageSize",5);
        params.put("published",1);
        request.setAttribute("pageName","首页");
        request.setAttribute("blogPageResult",blogService.getList(params));
        request.setAttribute("newBlogs", blogService.newBlogs());
        request.setAttribute("hotBlogs", blogService.hotBlogs());
        request.setAttribute("hotTags", tagService.hotTags());
        request.getSession().setAttribute("admin", adminService.get());
        return "visit/index";
    }

    @ApiOperation("根据条件查询博客列表")
    @GetMapping({"/blog/list"})
    public String searchBlog(HttpServletRequest request,
                            @RequestParam(value = "currPage",required = false) Integer currPage,
                             @RequestParam(value = "keyword",required = false) String keyword,
                             @RequestParam(value = "tagId",required = false) Integer tagId,
                             @RequestParam(value = "categoryId",required = false) Integer categoryId){
        Map<String,Object> params = new HashMap<>();
        params.put("currPage",currPage==null?1:currPage);
        params.put("pageSize",5);
        params.put("keyword",keyword);
        params.put("tagId",tagId);
        params.put("categoryId",categoryId);
        params.put("published",1);
        PageResult<Blog> blogPageResult=blogService.getList(params);
        request.setAttribute("pageName","条件查询");
        request.setAttribute("blogPageResult",blogPageResult);
        request.setAttribute("keyword",keyword);
        request.setAttribute("tagId",tagId);
        request.setAttribute("categoryId",categoryId);
        request.getSession().setAttribute("admin", adminService.get());
        String page=new String();
        if(keyword!=null)page="search";
        else if(tagId!=null&&tagId!=0)page="tagBlog";
        else page="categoryBlog";
        System.out.println("数据："+blogPageResult.getTotalCount());
        System.out.println("页面："+page);
        return "visit/"+page;
    }

    /**
     * 查询某篇博客
     */
    @ApiOperation("查询某篇博客")
    @GetMapping("/blog")
    public String blog(HttpServletRequest request,
                       @RequestParam(value="currPage",required = false)Integer currPage,
                       @RequestParam(value="id",required = false) Integer id){
        //增加访问量
        blogService.updateViews(id);
        Blog blog=blogService.getBlogById(id);
        String contentMd= MarkDownUtil.mdToHtml(blog.getContent());
        blog.setContent(contentMd);
        request.setAttribute("blog",blog);
        Map<String,Integer> params = new HashMap<>();
        params.put("currPage",currPage==null?1:currPage);
        params.put("pageSize",10);
        params.put("blogId",id);
        params.put("status",1);
        PageResult<Comment> comments=commentService.getList(params);
        request.setAttribute("pageName","博客详情");
        request.setAttribute("comments",comments);
        request.setAttribute("commentCount",comments.getTotalCount());
        request.setAttribute("blogId",id==null?request.getAttribute("blogId"):id);
        return "visit/blog";
    }

    @ApiOperation("新增评论")
    @PostMapping("/comment")
    @ResponseBody
    public String comment(HttpServletRequest request,
                          @RequestParam(value="blogId")Integer blogId,
                          @RequestParam(value="verifyCode")String verifyCode,
                          @RequestParam(value="commentator")String commentator,
                          @RequestParam(value="email")String email,
                          @RequestParam(value="content")String content){
        if(!verifyCode.equals(request.getSession().getAttribute("imgCode")))return "验证码错误！";
        Comment comment=new Comment(null,blogId,commentator,email,content,null,null,null,null,null);
        if(commentService.add(comment)) return "success";
        else return "评论失败";
    }

    /**
     * 查询所有分类
     */
    @GetMapping("/category")
    @ApiOperation("进入博客模块")
    public String category(HttpServletRequest request) {
        return this.categoryList(request, 1);
    }

    @ApiOperation("查询所有分类")
    @GetMapping("/category/list")
    public String categoryList(HttpServletRequest request,
                           @RequestParam(value = "currPage",required = false) Integer currPage){
        Map<String,Integer> params=new HashMap<>();
        params.put("currPage",currPage);
        params.put("pageSize",10);
        request.setAttribute("pageName","分类专栏");
        request.setAttribute("categoryPageResult",categoryService.getList(params));
        return "visit/category";
    }

    /**
     * 查询所有链接
     */
    @GetMapping("/link")
    @ApiOperation("进入链接模块")
    public String link(HttpServletRequest request) {
        return this.linkList(request, 1);
    }

    @ApiOperation("查询所有链接")
    @GetMapping("/link/list")
    public String linkList(HttpServletRequest request,
                               @RequestParam(value = "currPage",required = false) Integer currPage){
        Map<String,Integer> params=new HashMap<>();
        params.put("currPage",currPage);
        params.put("pageSize",10);
        request.setAttribute("pageName","推荐");
        request.setAttribute("linkPageResult",linkService.getList(params));
        return "visit/link";
    }
}
