package com.wang.service;

import com.wang.entity.Admin;

public interface AdminService {
    Admin loginVerify(String name, String password);

    Admin updateMessage(Integer id,String newName,String newNick,String img,String description);

    Admin updatePass(Integer id, String password, String oldPassword, String newPassword);

    Admin get();
}
