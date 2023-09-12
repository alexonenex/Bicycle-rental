package com.bicycle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;;
import com.bicycle.service.PlateDataService;
import com.bicycle.entity.PlateData;
import com.bicycle.entity.PlateDataList;
import com.bicycle.mapper.PlateDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Platedata)表服务实现类
 *
 * @author makejava
 * @since 2023-06-02 20:00:59
 */
@Service("platedataService")
public class PlateDataServiceImpl extends ServiceImpl<PlateDataMapper, PlateData> implements PlateDataService {

    @Autowired
    private PlateDataMapper plateDataMapper;

    @Override
    public List<PlateDataList> queryBicycleByType(String plate) {
        return plateDataMapper.queryBicycleByType(plate);
    }
}
