package com.wang.controller;

import com.wang.entity.Admin;
import com.wang.service.AdminService;
import com.wang.service.PublicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//这里不能用@RestController，否则方法返回的仅是字符串
@Controller
@Transactional
@Api(tags="管理员模块")
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private PublicService publicService;

    /**
     * 登录
     */
    @ApiOperation("显示登录界面")
    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @ApiOperation("登录验证")
    @PostMapping("/login")
    public String loginVerify(@RequestParam("name") String name,
                              @RequestParam("password") String password,
                              @RequestParam("verifyCode") String verifyCode,
                              HttpSession session) {
        //判断验证码是否一致
        String imgCode=session.getAttribute("imgCode")+"";
        if(!imgCode.equals(verifyCode)){
            session.setAttribute("errorMsg","验证码输入错误！");
            return "admin/login";
        }
        //核对用户名和密码
        Admin admin = adminService.loginVerify(name, password);
        if (admin == null) {
            session.setAttribute("errorMsg","用户名或密码错误！");
            return "admin/login";
        } else {
            //缓存管理员信息
            session.setAttribute("admin", admin);
            session.removeAttribute("errorMsg");
            return "redirect:/admin/index";
        }
    }


    /**
     * 登出
     */
    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }

    /**
     * 管理员首页
     */
    @ApiOperation("显示首页")
    @GetMapping({"", "/", "/index"})
    public String index(HttpServletRequest request) {
        //sider是用来设置侧边菜单栏选择效果的
        request.setAttribute("path", "index");
        //获得统计数据，类型为Map
        request.setAttribute("counts", publicService.getAllCount());
        return "admin/index";
    }

    /**
     * 信息修改
     */
    @ApiOperation("显示信息修改页面")
    @GetMapping("/message")
    public String message(HttpServletRequest request) {
        request.setAttribute("path","message");
        Admin admin=(Admin)request.getSession().getAttribute("admin");
        if(admin==null)return "admin/login";
        request.setAttribute("name",admin.getName());
        request.setAttribute("nick",admin.getNick());
        request.setAttribute("img",admin.getImg());
        request.setAttribute("description",admin.getDescription());
        return "admin/message";
    }

    @ApiOperation("修改登录名、昵称、头像")
    @PostMapping("/message/other")
    @ResponseBody
    public String updateName(HttpServletRequest request,
                             @RequestParam("newName")String newName,
                             @RequestParam("newNick")String newNick,
                             @RequestParam("img")String img,
                             @RequestParam("description")String description
    ) {
        Admin admin=(Admin)request.getSession().getAttribute("admin");
        Admin newAdmin=adminService.updateMessage(admin.getId(),newName,newNick,img,description);
        if(newAdmin!=null){
            newAdmin.setPassword(admin.getPassword());
            request.getSession().setAttribute("admin",newAdmin);
            return "success";
        }
        else return "修改失败";
    }

    @ApiOperation("修改密码")
    @PostMapping("/message/password")
    @ResponseBody
    public String updatePass(HttpServletRequest request,
                             @RequestParam("oldPassword")String oldPassword,
                             @RequestParam("newPassword")String newPassword) {
        Admin admin=(Admin)request.getSession().getAttribute("admin");
        Admin newAdmin=adminService.updatePass(admin.getId(),admin.getPassword(),oldPassword,newPassword);
        if(newAdmin!=null){
            newAdmin.setName(admin.getName());
            newAdmin.setNick(admin.getNick());
            request.getSession().setAttribute("admin",newAdmin);
            return "success";
        }
        else return "修改失败";
    }
}
