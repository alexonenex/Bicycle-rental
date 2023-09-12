package com.bicycle.controller;


import com.bicycle.common.R;
import com.bicycle.entity.OrderDepositList;
import com.bicycle.mapper.OrderDepositMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/deposit")
public class OrderDeposit {


    @Autowired
    private OrderDepositMapper orderDepositMapper;

    /*押金订单查询
    *
    *return
    * */
    @GetMapping("/queryOrderDepositList")
    public R<List<OrderDepositList>> queryOrderDepositList(){

        List<OrderDepositList> r=orderDepositMapper.queryOrderDepositList();


//    r=null;
        return r.size() == 0 ? R.error("未查询到押金订单数据！") : R.success(r);
    }
}
