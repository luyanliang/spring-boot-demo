package com.luke.excel.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/11/1
 */
@Data
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String mobile;
    private Integer age;
    private Date birthday;
    private Double score;
}
