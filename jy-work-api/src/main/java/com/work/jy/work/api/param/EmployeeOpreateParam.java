package com.work.jy.work.api.param;

import lombok.Data;

@Data
public class EmployeeOpreateParam {
    private Long id;

    private String username;

    private String password;

    private String lastPassword;

    private String name;
}
