package com.work.jy.work.aop.announce;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.work.jy.work.aop.feign.EmployeeService;
import com.work.jy.work.aop.model.EmployeeToken;
import com.work.jy.work.api.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class LoginTokenAspect {

    @Autowired
    private EmployeeService employeeService;

    public LoginTokenAspect() {
    }

    @Pointcut("@annotation(com.sd.cloud.user.aop.announce.UserLoginToken)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(userLoginToken)")
    public Object preHandle(ProceedingJoinPoint joinPoint , UserLoginToken userLoginToken) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        String token = request.getHeader("token");

        // 执行认证
        if (token == null) {
            return ResponseUtils.codeError(HttpStatus.UNAUTHORIZED.value(), "未包含令牌请求");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            return ResponseUtils.codeError(HttpStatus.UNAUTHORIZED.value(), "令牌不正确，解析有误");
        }
        EmployeeToken employeeToken = employeeService.getEmployeeTokenById(userId);
        if (employeeToken == null) {
            return ResponseUtils.httpCodeError(HttpStatus.FORBIDDEN.value(), "无权访问");
        }
        log.info("操作人："+employeeToken.getUsername()+"，操作时间："+ new Date());
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(employeeToken.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return ResponseUtils.codeError(HttpStatus.UNAUTHORIZED.value(), "登录已失效或者尚未登录");
        }
        refreshToken(token);
        HttpServletResponse respon = sra.getResponse();
        respon.setHeader("token",EmployeeThreadLocal.getToken());
        return joinPoint.proceed();

    }

    private void refreshToken(String token){
        Date outTime = JWT.decode(token).getExpiresAt();//过期时间
        Date now = new Date();
        long timeNum = outTime.getTime() - now.getTime();//距离过期时间毫秒数
        if(timeNum <= 1000 * 60 * 10 ) { //离过期时间仅剩不到十分钟，则重新生成一个token返回给前端
            Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 30);
            String userId = JWT.decode(token).getAudience().get(0);
            EmployeeToken employeeToken = employeeService.getEmployeeTokenById(userId);
            String newToken = JWT.create().withAudience(employeeToken.getId().toString()).withExpiresAt(date).sign(Algorithm.HMAC256(employeeToken.getPassword()));
            EmployeeThreadLocal.putToken(newToken);
        }else {
            EmployeeThreadLocal.putToken(token);
        }

    }
}
