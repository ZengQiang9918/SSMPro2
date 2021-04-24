package com.zengqiang.controller;

import com.zengqiang.bean.Employee;
import com.zengqiang.bean.Msg;
import com.zengqiang.bean.User;
import com.zengqiang.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 查出员工数据，初始化员工信息
     * 员工信息存在session中
     * @return
     */
    @ResponseBody
    @RequestMapping("/emp/init")
    public Msg doInit(HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        Employee employee = employeeService.queryEmployee(user);
        return Msg.success().add("employee",employee);
    }

    /**
     *
     * rest风格的请求，put表示更新
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{uid}",method = RequestMethod.PUT)
    public Msg doUpdateEmail(Employee employee,HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("user");
        //System.out.println(employee);
        //只更新邮箱，根据主键查找该员工
        int count = employeeService.updateEmployeeEmail(employee);
        if(count==1){
            //修改成功
            return Msg.success().add("tip","邮箱修改成功");
        }else {
            //修改失败
            return Msg.fail();
        }
    }

    /**
     * 退出登录
     */
    @ResponseBody
    @RequestMapping("/employee/logout")
    public Msg logOut(HttpServletRequest req){
        req.getSession().removeAttribute("user");
        return Msg.success();
    }
}
