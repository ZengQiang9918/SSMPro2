package com.zengqiang.service;

import com.zengqiang.bean.Employee;
import com.zengqiang.bean.EmployeeExample;
import com.zengqiang.bean.User;
import com.zengqiang.dao.EmployeeMapper;
import com.zengqiang.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查找员工
     * @param user
     * @return
     */
    public Employee queryEmployee(User user) {
        Integer uid=user.getUid();
        Employee employee = employeeMapper.selectByPrimaryKeyWithDeptUser(uid);
        return employee;
    }

    /**
     * 检验用户名是否可用
     * @param empName
     * @return true:代表当前姓名可用  false:不可用
     */
    public boolean checkUser(String empName) {
        EmployeeExample example =new EmployeeExample();
        //内部类
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count ==0;
    }

    /**
     * 保存员工
     * @param employee
     */
    public void saveEmp(Employee employee) {

        employeeMapper.insertSelective(employee);
    }

    /**
     * 按照员工ID查询员工
     * @param id
     * @return
     */
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * 员工更新
     * @param employee
     */
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 删除员工
     * @param id
     */
    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }


    /**
     * 批量删除员工
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        //按条件删除
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from xxx where emp_id in (1,2,3);
        criteria.andUidIn(ids);
        employeeMapper.deleteByExample(example);
    }








    /**
     * 更新员工的邮箱
     * @param employee
     * @return
     */
    public int updateEmployeeEmail(Employee employee) {
        int count = employeeMapper.updateByPrimaryKeySelective(employee);
        return count;
    }

    /**
     * 查找所有的员工连带部门信息
     * @return
     */
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDeptUser(null);
    }


    /**
     * 模糊查询
     * @param username
     * @return
     */
    public List<Employee> queryEmpLike(String username) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andUsernameLike(username);
        List<Employee> employees = employeeMapper.selectByExample(employeeExample);

        return employees;
    }


    /**
     * 查找所有的员工信息连带部门信息
     */
    public List<Employee> getAll1(List<Integer> uids){
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andUidIn(uids);
        List<Employee> employees = employeeMapper.selectByExampleWithDeptUser(employeeExample);
        return employees;
    }




}
