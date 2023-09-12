package com.bicycle.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LeaseAndCustomer {

    private String leaseInfoId;

    private String accountcustId;

    private String cName;

    private String cSex;

    private int cAge;

    private String cCard;

    private String cEmail;

    private String cAddress;

    private String bicycleinfoId;

    private int lNumber;

    private Date lDatefrist;

    private Date lTimefrist;

}
