package com.xlm.mysqldemo.pojo.master;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "user_operate_log")
public class UserOperateLogDO {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "operation")
    private String operation;

    @Column(name = "user_id")
    private String userId;
}
