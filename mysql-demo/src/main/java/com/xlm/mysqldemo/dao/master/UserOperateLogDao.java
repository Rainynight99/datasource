package com.xlm.mysqldemo.dao.master;

import com.xlm.mysqldemo.pojo.master.UserOperateLogDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOperateLogDao extends JpaRepository<UserOperateLogDO, Long> {

}
