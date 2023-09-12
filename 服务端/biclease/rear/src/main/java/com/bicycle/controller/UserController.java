package com.bicycle.controller;


import com.bicycle.common.R;
import com.bicycle.entity.Customer;
import com.bicycle.entity.OrderDepositList;
import com.bicycle.service.CustomerService;
import com.bicycle.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controller和ResponseBody注解的集合
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getall")
    public String getAll(){
        System.out.println("user get all");
        return null;
    }


    /*顾客信息查询
     *
     *return list<Customer>
     * */
    @GetMapping("/queryCustomerList")
    public R<List<Customer>> queryCustomerList(){

        List<Customer> r=customerService.queryCustomerList();
        return r.size() == 0 ? R.error("未查询到预约订单数据！") : R.success(r);
    }


}
