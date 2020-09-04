package com.fhmou.dao;

import com.fhmou.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/11/1
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
