package com.xlm.mysqldemo.pojo.slave;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

/**
 * @author xlm
 * @date 2023/7/25 下午3:19
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String password;
}
