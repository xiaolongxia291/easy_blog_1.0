package com.wang.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Admin {
    private Integer id;
    private String name;
    private String password;
    private String nick;
    private String img;
    private String description;

    public Admin(){
        id=null;
        name=null;
        password=null;
        nick=null;
        img=null;
        description=null;
    }
}
