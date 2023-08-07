package com.xlm.mysqldemo.service;

import com.xlm.mysqldemo.pojo.master.UserOperateLogDO;
import com.xlm.mysqldemo.pojo.slave.User;
import org.springframework.stereotype.Service;

/**
 * @author xlm
 * @date 2023/7/25 下午4:18
 */
public interface UserService {
    User saveUser(User user);

    UserOperateLogDO saveUserLog(UserOperateLogDO userOperateLogDO);
}
