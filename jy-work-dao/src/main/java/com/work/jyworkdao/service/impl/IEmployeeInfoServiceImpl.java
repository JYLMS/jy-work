package com.work.jyworkdao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.jyworkdao.entity.EmployeeInfo;
import com.work.jyworkdao.mapper.EmployeeInfoMapper;
import com.work.jyworkdao.service.IEmployeeInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author caoguangjun
 * @since 2019-09-17
 */
@Service
public class IEmployeeInfoServiceImpl extends ServiceImpl<EmployeeInfoMapper, EmployeeInfo> implements IEmployeeInfoService {

}
