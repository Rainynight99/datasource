package com.xlm.mysqldemo.service;

import com.xlm.mysqldemo.dao.master.UserOperateLogDao;
import com.xlm.mysqldemo.dao.slave.UserDao;
import com.xlm.mysqldemo.pojo.master.UserOperateLogDO;
import com.xlm.mysqldemo.pojo.slave.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xlm
 * @date 2023/7/25 下午2:51
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserOperateLogDao userOperateLogDao;

    @Resource
    private UserDao userDao;

    @Override
    public User saveUser(User user) {
        User save = userDao.save(user);
        return save;
    }


    @Override
    public UserOperateLogDO saveUserLog(UserOperateLogDO userOperateLogDO) {
        UserOperateLogDO save = userOperateLogDao.save(userOperateLogDO);
        return save;
    }


}
