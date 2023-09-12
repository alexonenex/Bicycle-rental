package com.bicycle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bicycle.entity.PlateData;
import com.bicycle.entity.PlateDataList;


import java.util.List;


/**
 * (Platedata)表服务接口
 *
 * @author makejava
 * @since 2023-06-02 20:00:59
 */
public interface PlateDataService extends IService<PlateData> {

    /**
     * 根据板块查询数据
     * @param plate
     * @return List<PlateDataList>
     */
    public List<PlateDataList> queryBicycleByType(String plate);
}
