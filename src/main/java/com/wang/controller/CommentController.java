package com.wang.controller;

import com.wang.service.CommentService;
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
@Api(tags="评论模块")
@Transactional
public class CommentController {
    @Resource
    private CommentService commentService;


    @ApiOperation("进入评论管理模块")
    @GetMapping("/admin/comment")
    public String page(HttpServletRequest request) {
        request.setAttribute("path", "comment");
        return "admin/comment";
    }

    /**
     *查询评论列表
     */
    @ApiOperation("管理端查询所有评论列表")
    @GetMapping("/admin/comment/list")
    @ResponseBody
    //params中应有参数：currPage
    public PageResult listAdmin(@RequestParam("currPage")Integer currPage,
                                @RequestParam("pageSize")Integer pageSize) {
        Map<String,Integer> params = new HashMap<>();
        params.put("currPage",currPage);
        params.put("pageSize",pageSize);
        params.put("blogId",0);
        return commentService.getList(params);
    }

    /**
     * 删除评论
     */
    @ApiOperation("管理端批量删除评论")
    @PostMapping("/admin/comment/delete")
    @ResponseBody
    public String delete(@RequestBody Integer[] ids){
        int deleteOrCheck=0;
        if(commentService.deleteOrCheck(ids,deleteOrCheck))return "success";
        else return "删除失败";
    }

    /**
     * 审核评论
     */
    @ApiOperation("管理端批量审核评论")
    @PostMapping("/admin/comment/check")
    @ResponseBody
    public String check(@RequestBody Integer[] ids){
        int deleteOrCheck=1;
        if(commentService.deleteOrCheck(ids,deleteOrCheck))return "success";
        else return "删除失败";
    }

    /**
     * 回复评论
     */
    @ApiOperation("管理端回复评论")
    @PostMapping("/admin/comment/reply")
    @ResponseBody
    public String reply(@RequestParam("id")Integer id,
                        @RequestParam("reply")String reply){
       if(commentService.reply(id,reply))return "success";
       else return "回复失败";
    }
}
