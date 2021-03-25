package com.wang.service;

import com.wang.dao.AdminDao;
import com.wang.entity.Admin;
import com.wang.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    //登录验证用户名和密码
    @Override
    public Admin loginVerify(String name, String password) {
        Admin admin=new Admin(null,name, MD5Util.MD5Encode(password,"UTF-8"),null,null,null);
        return adminDao.getAdmin(admin);
    }

    //修改登录名、昵称、头像、网站描述
    @Override
    public Admin updateMessage(Integer id,String newName,String newNick,String img,String description) {
        Admin admin=new Admin(id,newName,null,newNick,img,description);
        adminDao.updateAdmin(admin);
        return admin;
    }

    //修改密码
    @Override
    public Admin updatePass(Integer id, String password, String oldPassword, String newPassword) {
        if(MD5Util.MD5Encode(oldPassword,"UTF-8").equals(password)){
            Admin admin=new Admin(id,null,MD5Util.MD5Encode(newPassword,"UTF-8"),null,null,null);
            adminDao.updateAdmin(admin);
            return admin;
        }
        else return null;
    }

    @Override
    public Admin get() {
        return adminDao.getAdmin(new Admin());
    }
}
