package com.work.jy.work.api.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.work.jy.work.aop.announce.EmployeeThreadLocal;
import com.work.jy.work.aop.model.EmployeeToken;
import com.work.jy.work.api.param.EmployeeLoginParam;
import com.work.jy.work.api.param.EmployeeOpreateParam;
import com.work.jy.work.api.service.EmployeeInfoService;
import com.work.jy.work.api.util.Md5Utils;
import com.work.jy.work.api.util.ResponseUtils;
import com.work.jyworkdao.entity.EmployeeInfo;
import com.work.jyworkdao.service.IEmployeeInfoService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 *
 */
@Service
public class EmployeeInfoServiceImpl implements EmployeeInfoService {

    @Autowired
    IEmployeeInfoService employeeInfoService;

    @Override
    public ResponseEntity employeeLogin(EmployeeLoginParam loginVo) {
        if (loginVo == null) {
            return ResponseUtils.error("参数错误！");
        }
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        wrapper.eq("is_delete", 0);
        EmployeeInfo employeeInfo = employeeInfoService.getOne(wrapper);

        if (employeeInfo == null) {
            return ResponseUtils.error("用户名不存在！");
        }

        String calcPwd = Md5Utils.inputPassToDbPass(password, employeeInfo.getSalt());
        if (!StringUtils.equals(calcPwd, employeeInfo.getPassword())) {
            return ResponseUtils.error("密码错误！");
        }

        getToken(employeeInfo);//生成token放入ThreadLocal中

        MultiValueMap dataMap = new LinkedMultiValueMap();
        dataMap.add("token", EmployeeThreadLocal.getToken());
        dataMap.add("employeeInfo",employeeInfo);

        return ResponseUtils.success("ok",dataMap);
    }

    @Override
    public void addEmployee(EmployeeOpreateParam employeeVO) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", employeeVO.getUsername());
        wrapper.eq("is_delete", 0);
        EmployeeInfo employeeInfo = employeeInfoService.getOne(wrapper);
        if(employeeInfo != null) {
            throw new RuntimeException("用户名已存在！");
        }else {
            EmployeeInfo employee = new EmployeeInfo();
            employee.setUsername(employeeVO.getUsername());
            int saltNum = (int)(Math.random() * 1000000);//随机生成一个六位整数作为盐值
            String salt = String.valueOf(saltNum);
            employee.setSalt(salt);
            //密码加密
            String calcPwd = Md5Utils.inputPassToDbPass(employeeVO.getPassword(), employee.getSalt());
            employee.setPassword(calcPwd);
            employee.setName(employeeVO.getName());
            employee.setIsDelete(false);
            employee.setCreateTime(LocalDateTime.now());
            employee.setUpdateTime(LocalDateTime.now());

            employeeInfoService.save(employee);
        }
    }

    @Override
    public void deleteEmployee(EmployeeOpreateParam employeeVO) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", employeeVO.getUsername());
        wrapper.eq("is_delete", 0);
        EmployeeInfo employeeInfo = employeeInfoService.getOne(wrapper);
        if(employeeInfo == null) {
            throw new RuntimeException("用户名不存在！");
        }else {
            employeeInfo.setIsDelete(true);
            employeeInfoService.updateById(employeeInfo);
        }
    }

    @Override
    public void resetPassword(EmployeeOpreateParam employeeVO) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", employeeVO.getUsername());
        wrapper.eq("is_delete", 0);
        EmployeeInfo employeeInfo = employeeInfoService.getOne(wrapper);
        if(employeeInfo == null) {
            throw new RuntimeException("用户名不存在！");
        }else {
            String calcPwd = Md5Utils.inputPassToDbPass(employeeVO.getLastPassword(), employeeInfo.getSalt());
            if (!StringUtils.equals(calcPwd, employeeInfo.getPassword())) {
                throw new RuntimeException("密码错误！");
            }else{
                String newPassword = Md5Utils.inputPassToDbPass(employeeVO.getPassword(), employeeInfo.getSalt());
                employeeInfo.setPassword(newPassword);
                employeeInfoService.updateById(employeeInfo);
            }
        }
    }

    @Override
    public List<EmployeeInfo> getPageList(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_delete", 0);
        if(!StringUtils.isBlank( username)) {
            wrapper.eq("username", username);
        }
        List<EmployeeInfo> employeeInfoList = employeeInfoService.list(wrapper);
        return employeeInfoList;
    }

    @Override
    public EmployeeToken getEmployeeTokenById(String id) {
        Long idNum = new Long(id) ;
        EmployeeToken result = null;
        EmployeeInfo employeeInfo = employeeInfoService.getById(idNum);
        if(employeeInfo != null) {
            result = convertToEmployeeToken(employeeInfo);
        }
        return result;
    }


    public String getToken(EmployeeInfo employeeInfo) {
        /**
         * 设置半小时有效,添加参数withClaim,设置过期时间withExpiresAt.
         * 使用个人密码作为密钥
         *
         *
         */
        Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 30);
        String token = JWT.create().withAudience(employeeInfo.getId().toString()).withExpiresAt(date).sign(Algorithm.HMAC256(employeeInfo.getPassword()));
        EmployeeThreadLocal.putToken(token);
        EmployeeToken employeeToken = convertToEmployeeToken(employeeInfo);
        EmployeeThreadLocal.putEmployee(employeeToken);
        return token;
    }

    private EmployeeToken convertToEmployeeToken(EmployeeInfo employeeInfo){
        EmployeeToken employeeToken = new EmployeeToken();
        employeeToken.setId(employeeInfo.getId());
        employeeToken.setUsername(employeeInfo.getUsername());
        employeeToken.setPassword(employeeInfo.getPassword());
        employeeToken.setName(employeeInfo.getName());
        employeeToken.setIsDelete(employeeInfo.getIsDelete());
        employeeToken.setCreateTime(employeeInfo.getCreateTime());
        employeeToken.setUpdateTime(employeeInfo.getUpdateTime());
        return employeeToken;
    }

}
