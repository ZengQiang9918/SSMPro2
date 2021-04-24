package com.zengqiang.bean;

public class User {
    private Integer uid;

    private String identity;

    private String username;

    private String password;

    public User() {
    }

    public User(Integer uid, String identity, String username, String password) {
        this.uid = uid;
        this.identity = identity;
        this.username = username;
        this.password = password;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }


    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", identity='" + identity + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}