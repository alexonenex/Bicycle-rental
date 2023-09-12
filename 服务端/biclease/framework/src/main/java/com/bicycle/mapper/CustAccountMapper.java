package com.bicycle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bicycle.entity.CustAccount;
import com.bicycle.entity.dto.RegisterAccountDto;
import org.springframework.stereotype.Repository;

@Repository
public interface CustAccountMapper extends BaseMapper<CustAccount> {

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
     * @return int
     */
    int registerAccount(RegisterAccountDto registerAccountDto);
    int registerAccountInfo(RegisterAccountDto registerAccountDto);
}