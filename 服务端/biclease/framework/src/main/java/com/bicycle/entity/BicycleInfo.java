package com.bicycle.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自行车信息表
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bicycleinfo")
public class BicycleInfo {
    @TableId
    private String bicycleinfoId;

    private String storeId;

    private String depositId;

    private String currentpriceId;

    private String bBrand;

    private String bName;

    private String bType;

    private int bStock;

    private String bImage;

    private String bImgurl;

    private String bEssay;
}