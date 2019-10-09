package com.work.jy.work.api.controller;


import com.work.jy.work.aop.announce.EmployeeThreadLocal;
import com.work.jy.work.aop.announce.UserLoginToken;
import com.work.jy.work.aop.model.EmployeeToken;
import com.work.jy.work.api.param.EmployeeLoginParam;
import com.work.jy.work.api.param.EmployeeOpreateParam;
import com.work.jy.work.api.service.impl.EmployeeInfoServiceImpl;
import com.work.jy.work.api.util.ResponseUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value="/employee")
@Slf4j
public class EmployeeInfoController {

    @Autowired
    private EmployeeInfoServiceImpl employeeInfoService;


    @ApiOperation(httpMethod = "POST", value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String", example = "123456@qq.com"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", required = true, dataType = "String", example = "123456")
    })
    @PostMapping("/employeeLogin")
    public ResponseEntity login(@RequestBody EmployeeLoginParam loginVo) {
        return employeeInfoService.employeeLogin(loginVo);
    }


    @ApiOperation(httpMethod = "GET",value = "获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = false, dataType = "String", example = "123456@qq.com")
    })
    @GetMapping(value = "/getEmployeeList")
    @UserLoginToken
    public ResponseEntity getEmployeeList(String username) {
        return ResponseUtils.success("ok",employeeInfoService.getPageList(username));
    }



    @ApiOperation(httpMethod = "POST", value = "添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String", example = "zjf123"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", required = true, dataType = "String", example = "zjf123"),
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = true, dataType = "String", example = "123456")
    })
    @PostMapping("/employeeOperate")
    @UserLoginToken
    public ResponseEntity addEmployee(@RequestBody EmployeeOpreateParam employeeVO) {
        employeeInfoService.addEmployee(employeeVO);
        return ResponseUtils.success("添加成功");
    }


    @ApiOperation(httpMethod = "DELETE", value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String", example = "zjf123")
    })
    @DeleteMapping("/employeeOperate")
    @UserLoginToken
    public ResponseEntity deleteEmployee(@RequestBody EmployeeOpreateParam employeeVO) {
        employeeInfoService.deleteEmployee(employeeVO);
        return ResponseUtils.success("删除成功");
    }

    @ApiOperation(httpMethod = "PUT", value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String", example = "zjf123"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", required = true, dataType = "String", example = "zjf123"),
            @ApiImplicitParam(name = "lastPassword", value = "上次密码", paramType = "query", required = true, dataType = "String", example = "123456")
    })
    @PutMapping("/resetPassword")
    @UserLoginToken
    public ResponseEntity resetPassword(@RequestBody EmployeeOpreateParam employeeVO) {
        employeeInfoService.resetPassword(employeeVO);
        return ResponseUtils.success("重置成功");
    }



    @ApiOperation(httpMethod = "POST", value = "测试token")
    @UserLoginToken
    @PostMapping("/getMessage")
    public ResponseEntity getMessage(){
         log.info("你已通过验证");
        MultiValueMap tokenMap = new LinkedMultiValueMap();
        tokenMap.add("token", EmployeeThreadLocal.getToken());

        return ResponseUtils.success("你已通过验证",tokenMap);
    }


    @ApiOperation(httpMethod = "POST", value = "测试Permission")
    @PostMapping("/getPermission")
    public ResponseEntity getPermission(){
        log.info("你已通过验证");
        MultiValueMap tokenMap = new LinkedMultiValueMap();
        tokenMap.add("token", EmployeeThreadLocal.getToken());

        return ResponseUtils.success("你已通过验证",tokenMap);
    }


    @ApiOperation(httpMethod = "GET",value = "获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", paramType = "query", required = false, dataType = "String", example = "123")
    })
    @GetMapping(value = "/getEmployeeTokenById")
    public EmployeeToken getEmployeeTokenById(@RequestParam String id){
        return employeeInfoService.getEmployeeTokenById(id);
    }

}
