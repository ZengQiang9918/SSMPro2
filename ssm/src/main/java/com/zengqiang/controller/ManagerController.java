package com.zengqiang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zengqiang.bean.Employee;
import com.zengqiang.bean.Msg;
import com.zengqiang.bean.User;
import com.zengqiang.dao.UserMapper;
import com.zengqiang.service.EmployeeService;
import com.zengqiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ManagerController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;


    //该方法封装给分页方法用
    public List<Employee> getEmployees(String username,List<Integer> uids){
        if("undefined".equals(username)){
            List<Employee> employees = employeeService.getAll();
            return employees;
        } else if(uids.size()!=0){
            //如果uids.size()不是空的话，说明模糊查询是有找到值的
            List<Employee> employees1 = employeeService.getAll1(uids);
            return employees1;
        }
        //uids.size()为0，模糊查询搜索的结果为“无”
        return null;

    }


    /**
     * 查询员工分页信息
     * 导入json的包jackson
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emps")
    public Msg getEmpsWithJSon(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                               String username,Model model){
        List<Integer> uids=null;
        //如果username不是undefined，表示需要进行模糊查询
        if(!"undefined".equals(username)){
            //模糊查询
            String usernameLike="%"+username+"%";
            List<Employee> employees = employeeService.queryEmpLike(usernameLike);
            uids =new ArrayList<>();
            for (Employee employee : employees) {
                uids.add(employee.getUid());
            }
        }
        PageHelper.startPage(pn,5);
        //***切记，该方法只对紧跟其后的sql语句进行封装，所以后面一定不能跟着两条sql
        //startPage后面紧跟的查询就是一个分页查询
        List<Employee> emps = getEmployees(username,uids);
        System.out.println(emps);
        //使用pageInfo包装我们查询后的结果，只需要将pageInfo交给我们的页面即可
        //pageInfo里面封装了详细的信息，包括有我们查询出来的数据
        //第二个参数为连续显示的页数。
        PageInfo page = new PageInfo(emps,5);

        //如果让我来写Msg这个类的话应该是会通过简单构造和set传参的方法设置属性
        return Msg.success().add("pageInfo",page);
    }

    /**
     * 检查用户名是否可用
     * @param empName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkUser")
    public Msg checkUser(String empName){
        //先判断用户名是否是合法的表达式
        //java中的正则表达式没有斜杆
        String regx ="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        boolean matches = empName.matches(regx);
        if(!matches){
            return Msg.fail().add("va_msg","用户名必须是6-16位英文和数字的组合或者2-5位的中文");
        }

        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名不可用");
        }
    }

    /**
     * 员工保存
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/employee",method = RequestMethod.POST)
    public Msg saveEmp(Employee employee){
        //先保存User,再保存employee
        User user = new User();
        user.setIdentity("0");
        user.setPassword("666");
        user.setUsername(employee.getUsername());
        userService.saveUser(user);

        List<User> users = userService.queryUser(user);
        employee.setUid(users.get(0).getUid());
        employeeService.saveEmp(employee);
        return Msg.success();
    }

    /**
     *  根据id查询员工
     * @return
     */
    @RequestMapping(value = "/employee/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    /**
     *  员工更新方法
     */
    @ResponseBody
    @RequestMapping(value = "/employee/{uid}",method = RequestMethod.PUT)
    public  Msg saveEmp(@PathVariable("uid") String uid, Employee employee,
                        HttpServletRequest request){
        //System.out.println(request.getParameter("email"));
        //了解一下@PathVirable注解
        System.out.println(uid);
        System.out.println("将要更新的员工数据："+employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 单个批量二合一的方法
     * 批量删除: 1-2-3
     * 单个删除：1
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/employee/{ids}",method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids") String ids){
        if(ids.contains("-")){
            List<Integer> del_ids =new ArrayList<>();
            //多个的批量删除
            String[] str_ids = ids.split("-");
            //组装id
            for (String str_id : str_ids) {
                del_ids.add(Integer.parseInt(str_id));
            }
            //先删除employee
            employeeService.deleteBatch(del_ids);
            //再删除user
            userService.deleteBatch(del_ids);
        }else{
            Integer id =Integer.parseInt(ids);
            //先删除employee
            employeeService.deleteEmp(id);
            //再删除user
            userService.deleteUser(id);
        }
        return Msg.success();
    }

    /**
     * 模糊查询
     */
    @ResponseBody
    @RequestMapping(value = "employeeQuery/{username}",method = RequestMethod.GET)
    public Msg queryEmp(@PathVariable("username") String username){
        //需要手动加上%username%
        String usernameLike="%"+username+"%";
        List<Employee> employees = employeeService.queryEmpLike(usernameLike);


        return Msg.success();
    }

    /**
     * 退出登录
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/manager/logout")
    public Msg logOut(HttpServletRequest req){
        req.getSession().removeAttribute("user");
        return Msg.success();
    }


}
