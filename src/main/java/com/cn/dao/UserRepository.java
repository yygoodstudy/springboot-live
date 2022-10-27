package com.cn.dao;

import com.cn.pojo.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @Author YaoJie
 * @Date 2021/10/19
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByLoginName(String loginName);
}
