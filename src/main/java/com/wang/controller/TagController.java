package com.wang.controller;

import com.wang.service.TagService;
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
@Api(tags="标签模块")
@Transactional
public class TagController {
    @Resource
    private TagService tagService;

    @ApiOperation("进入标签管理模块")
    @GetMapping("/admin/tag")
    public String page(HttpServletRequest request) {
        request.setAttribute("path", "tag");
        return "admin/tag";
    }

    /**
     *查询分类列表
     */
    @ApiOperation("管理端查询所有标签列表")
    @GetMapping("/admin/tag/list")
    @ResponseBody
    //params中应有参数：currPage
    public PageResult listAdmin(@RequestParam("currPage")Integer currPage,
                                @RequestParam("pageSize")Integer pageSize) {
        Map<String,Integer> params = new HashMap<>();
        params.put("currPage",currPage);
        params.put("pageSize",pageSize);
        return tagService.getList(params);
    }

    /**
     * 删除标签
     */
    @ApiOperation("管理端批量删除标签")
    @PostMapping("/admin/tag/delete")
    @ResponseBody
    public String delete(@RequestBody Integer[] ids){
        if(tagService.delete(ids))return "success";
        else return "有关联数据，删除失败";
    }

    /**
     * 新增标签
     */
    @ApiOperation("新增标签")
    @PostMapping("/admin/tag/name={name}")
    @ResponseBody
    public String add(@PathVariable("name") String name){
        if(tagService.add(name))return "success";
        else return "名称重复!";
    }
}
