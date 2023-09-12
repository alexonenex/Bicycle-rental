package com.bicycle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bicycle.entity.PlateData;
import com.bicycle.entity.PlateDataList;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlateDataMapper extends BaseMapper<PlateData> {

    /**
     * 根据板块查询数据
     * @param plate
     * @return List<PlateDataList>
     */
    public List<PlateDataList> queryBicycleByType(String plate);
}
