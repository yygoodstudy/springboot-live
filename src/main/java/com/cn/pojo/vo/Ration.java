package com.cn.pojo.vo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */

@Data
@Entity
@Table(name = "rotation_image")
public class Ration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_index")
    private Integer imageIndex;

    @Column(name = "status")
    private String status;

    private String url;


}
