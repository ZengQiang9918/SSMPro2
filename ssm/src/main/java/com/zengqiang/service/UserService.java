package com.zengqiang.service;

import com.zengqiang.bean.User;
import com.zengqiang.bean.UserExample;
import com.zengqiang.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> queryUser(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    /**
     * 插入用户信息
     * @param user
     */
    public void saveUser(User user) {
        userMapper.insertSelective(user);
    }

    /**
     * 删除用户信息
     * @param id
     */
    public void deleteUser(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除用户信息
     * @param del_ids
     */
    public void deleteBatch(List<Integer> del_ids) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUidIn(del_ids);
        userMapper.deleteByExample(userExample);
    }
}
