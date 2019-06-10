package com.cc.yhzt.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.yhzt.entity.AccountData;
import com.cc.yhzt.mapper.AccountDataMapper;
import com.cc.yhzt.service.IAccountDataService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CC
 * @since 2019-05-18
 */
@Service
@DS("ls")
public class AccountDataServiceImpl extends ServiceImpl<AccountDataMapper, AccountData> implements IAccountDataService {

}
