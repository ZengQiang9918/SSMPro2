package com.zengqiang.bean;

public class Manager {
    private Integer uid;

    private String username;

    public Manager() {
    }

    public Manager(Integer uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Override
    public String toString() {
        return "Manager{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                '}';
    }
}