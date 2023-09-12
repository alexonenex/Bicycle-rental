package com.bicycle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bicycle.entity.CustAccount;
import com.bicycle.entity.dto.RegisterAccountDto;

public interface CustAccountService extends IService<CustAccount> {

    /**
     * 修改密码
     * @param email
     * @param password
     * @return int
     */
    public int resettingAccount(String email, String password);


    /**
     * 注册
     * @param registerAccountDto
     * @return
     */
    public int registerAccount(RegisterAccountDto registerAccountDto);
}
