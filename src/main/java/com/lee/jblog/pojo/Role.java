package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class Role {

    private int ID;

    private String name;

    public Role(){}

    public Role(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }
}
