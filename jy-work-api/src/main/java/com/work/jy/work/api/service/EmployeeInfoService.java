package com.work.jy.work.api.service;

import com.work.jy.work.aop.model.EmployeeToken;
import com.work.jy.work.api.param.EmployeeLoginParam;
import com.work.jy.work.api.param.EmployeeOpreateParam;
import com.work.jyworkdao.entity.EmployeeInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface EmployeeInfoService {
    ResponseEntity employeeLogin(EmployeeLoginParam loginVo);

    void addEmployee(EmployeeOpreateParam employeeVo);

    void deleteEmployee(EmployeeOpreateParam employeeVO);

    void resetPassword(EmployeeOpreateParam employeeVO);

    List<EmployeeInfo> getPageList(String username);

    EmployeeToken getEmployeeTokenById(String id);
}
