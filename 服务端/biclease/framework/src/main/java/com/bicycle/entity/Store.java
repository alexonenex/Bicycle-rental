package com.bicycle.entity;

import lombok.Data;

/**
 * 连锁店表
 */
@Data
public class Store {
    private String storeId;

    private String sName;

    private String sAddress;

    private String sPhone;

    private int sStatus;
}