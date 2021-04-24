package com.zengqiang.bean;

public class Employee {
    private Integer uid;

    private String username;

    private String gender;

    private String email;

    private Integer did;

    //我们希望在构建分页数据时一并查出员工的信息包括部门（联合查询）
    private Department department;

    //我们希望在员工界面可以显示员工的密码，身份等(只查询一个员工即可)
    private User user;

    public Employee() {
    }

    public Employee(Integer uid, String username, String gender, String email, Integer did) {
        this.uid = uid;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.did = did;
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", did=" + did +
                '}';
    }
}