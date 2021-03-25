package com.wang.controller;

import com.wang.service.LinkService;
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
@Api(tags="链接模块")
@Transactional
public class LinkController {
    @Resource
    private LinkService linkService;

    @ApiOperation("进入链接管理模块")
    @GetMapping("/admin/link")
    public String page(HttpServletRequest request) {
        request.setAttribute("path", "link");
        return "admin/link";
    }

    /**
     *查询链接列表
     */
    @ApiOperation("管理端查询所有链接列表")
    @GetMapping("/admin/link/list")
    @ResponseBody
    //params中应有参数：currPage
    public PageResult listAdmin(@RequestParam("currPage")Integer currPage,
                                @RequestParam("pageSize")Integer pageSize) {
        Map<String,Integer> params = new HashMap<>();
        params.put("currPage",currPage);
        params.put("pageSize",pageSize);
        return linkService.getList(params);
    }

    /**
     * 删除链接
     */
    @ApiOperation("管理端批量删除链接")
    @PostMapping("/admin/link/delete")
    @ResponseBody
    public String delete(@RequestBody Integer[] ids){
        if(linkService.delete(ids))return "success";
        else return "删除失败";
    }

    /**
     * 新增、修改链接
     */
    @ApiOperation("新增、修改链接")
    @PostMapping("/admin/link/save")
    @ResponseBody
    //需要name、url、description、rank
    public String save(@RequestParam("id")Integer id,
                       @RequestParam("url")String url,
                       @RequestParam("name")String name,
                       @RequestParam("description")String description,
                       @RequestParam("rank")Integer rank){
        if(linkService.save(id,url,name,description,rank))return "success";
        else return "提交失败";
    }
}
