package com.work.jy.work.aop.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeToken {
    private Long id;

    private String username;

    private String password;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDelete;
}
