package com.bicycle.entity;

import lombok.Data;

/**
 * 时价表
 */
@Data
public class CurrentPrice {
    private String currentpriceId;

    private String cPrimary;

    private String cSecondary;

    private double cPrice;
}