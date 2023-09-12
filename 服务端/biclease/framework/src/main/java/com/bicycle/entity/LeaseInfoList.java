package com.bicycle.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LeaseInfoList {

    private String leaseinfoId;

    private String bicycleinfoId;

    private String accountcustId;

    private String orderdepositId;

    private Date lDatefrist;

    private Date lTimefrist;

    private Date lDatesecond;

    private Date lTimesecond;

    private Date lDuration;

    private double lMoney;

    private int lNumber;

    private int lStatus;

    private String cName;

    private String bBrand;

    private String bName;

    private double dPrice;

    private double cPrice;
}
