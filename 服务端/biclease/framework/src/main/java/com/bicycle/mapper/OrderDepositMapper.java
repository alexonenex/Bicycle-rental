package com.bicycle.mapper;


import com.bicycle.entity.OrderDepositList;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OrderDepositMapper {


    /*押金订单查询
    *
    *return List<OrderDepositList>
    * */
    public List<OrderDepositList> queryOrderDepositList();


}