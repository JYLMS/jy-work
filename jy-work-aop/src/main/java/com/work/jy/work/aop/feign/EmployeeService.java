package com.work.jy.work.aop.feign;


import com.work.jy.work.aop.model.EmployeeToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Service(value = "employeeService")
@FeignClient(name= "sdcloud-user-api" ,contextId ="employee")
public interface EmployeeService {

    /**
     * feign 获取employee
     * */
    @RequestMapping(value="/employee/getEmployeeTokenById",method= RequestMethod.GET)
    EmployeeToken getEmployeeTokenById(@RequestParam(name = "id") String id);
}
