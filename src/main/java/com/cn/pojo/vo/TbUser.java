package com.cn.pojo.vo;

import lombok.*;

/**
 * @Author YaoJie
 * @Date 2022/3/7
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TbUser {

    private Integer id;

    private String name;

    private Integer age;

    private String address;

}
