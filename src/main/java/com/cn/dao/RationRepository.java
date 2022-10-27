package com.cn.dao;


import com.cn.pojo.vo.Ration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */
public interface RationRepository extends JpaRepository<Ration,Integer> {

    List<Ration> findByStatus(String status);
}
