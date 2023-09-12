package com.bicycle.entity;

import lombok.Data;

/**
 * 区间押金表
 */
@Data
public class Depositi {
    private String depositId;

    private String dPrimary;

    private String dSecondary;

    private double dPrice;
}