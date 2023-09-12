package com.bicycle.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDepositList {

    private String orderdepositId;

    private double oPrice;

    private int oStatus;

    private String accountcustId;

    private String cName;

    private String bBrand;

    private String bName;

    private Date lDatefrist;
}
