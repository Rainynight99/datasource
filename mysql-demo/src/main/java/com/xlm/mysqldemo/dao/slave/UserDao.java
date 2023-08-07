package com.xlm.mysqldemo.dao.slave;

import com.xlm.mysqldemo.pojo.slave.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

}
