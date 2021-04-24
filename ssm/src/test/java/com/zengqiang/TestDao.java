package com.zengqiang;

import com.zengqiang.bean.*;
import com.zengqiang.dao.DepartmentMapper;
import com.zengqiang.dao.EmployeeMapper;
import com.zengqiang.dao.ManagerMapper;
import com.zengqiang.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

/**
 * 测试dao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestDao {
    /*注入接口的实现类，测试dao接口的准确性*/
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void testDepartmentMapper(){
        //Department department = new Department(null,"开发部");
        //Department department = new Department(null,"测试部");
        //Department department = new Department(null,"运维部");
        //departmentMapper.insertSelective(department);
    }

    @Test
    public void testUserMapper(){


        //注册（插入）5个管理员账号
        UserMapper mapper = sqlSessionTemplate.getMapper(UserMapper.class);
        /*for(int i=0;i<5;i++){
            String username= UUID.randomUUID().toString().substring(0,5);
            User user = new User(null, "1", username+i, "666");
            mapper.insertSelective(user);
        }*/

        //往manager表插入5个管理员
        /*UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdentityEqualTo("1");
        List<User> users = userMapper.selectByExample(userExample);
        for (User user : users) {
            Manager manager = new Manager(user.getUid(), user.getUsername());
            managerMapper.insertSelective(manager);
        }*/


        //注册(插入)1000个普通员工
        //System.out.println(UUID.randomUUID().toString().substring(0,5));
        /*for (int i=0;i<1000;i++){
            String username= UUID.randomUUID().toString().substring(0,5);
            User user = new User(null, "0", username+i, "666");
            mapper.insertSelective(user);
        }*/

        //往Employee表中插入1000个普通员工
        /*List<User> users = userMapper.selectByExample(null);
        for (User user : users) {
            //不能让uid设为null，因为它有外键约束
            Employee employee =new Employee(user.getUid(),user.getUsername(),"M",user.getUsername()+"@qq.com",1);
            employeeMapper.insertSelective(employee);
        }*/



    }

    @Test
    public void test02(){
        Employee employee = employeeMapper.selectByPrimaryKeyWithDeptUser(11);
        System.out.println(employee);
        Department department = employee.getDepartment();
        System.out.println(department);
        User user = employee.getUser();
        System.out.println(user);
    }
}
