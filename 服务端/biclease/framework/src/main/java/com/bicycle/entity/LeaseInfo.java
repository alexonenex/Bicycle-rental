package com.bicycle.entity;

import lombok.Data;

import java.util.Date;

/**
 * 租借信息表
 */
@Data
public class LeaseInfo {
    private String leaseInfoId;

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
}