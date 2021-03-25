package com.wang.controller;

import com.wang.service.CategoryService;
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
@Api(tags="分类模块")
@Transactional
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @ApiOperation("进入分类管理模块")
    @GetMapping("/admin/category")
    public String page(HttpServletRequest request) {
        request.setAttribute("path", "category");
        return "admin/category";
    }

    /**
     *查询分类列表
     */
    @ApiOperation("管理端查询所有分类列表")
    @GetMapping("/admin/category/list")
    @ResponseBody
    //params中应有参数：currPage
    public PageResult listAdmin(@RequestParam("currPage")Integer currPage,
                                @RequestParam("pageSize")Integer pageSize) {
        Map<String,Integer> params = new HashMap<>();
        params.put("currPage",currPage);
        params.put("pageSize",pageSize);
        return categoryService.getList(params);
    }

    /**
     * 删除分类
     */
    @ApiOperation("管理端批量删除分类")
    @PostMapping("/admin/category/delete")
    @ResponseBody
    public String delete(@RequestBody Integer[] ids){
        if(categoryService.delete(ids))return "success";
        else return "有关联数据，删除失败";
    }


    /**
     * 新增/修改分类
     */
    @ApiOperation("新增、修改分类")
    @PostMapping("/admin/category/save")
    @ResponseBody
    //需要name、img
    public String add(@RequestParam("name") String name,
                      @RequestParam("img") String img,
                      @RequestParam("id") Integer id){
        if(categoryService.save(id,name,img))return "success";
        else return "提交失败";
    }
}
