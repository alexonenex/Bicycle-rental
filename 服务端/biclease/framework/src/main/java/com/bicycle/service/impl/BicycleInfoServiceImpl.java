package com.bicycle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.bicycle.service.BicycleInfoService;
import com.bicycle.entity.BicycleInfo;
import com.bicycle.mapper.BicycleInfoMapper;
import org.springframework.stereotype.Service;

/**
 * (Bicycleinfo)表服务实现类
 *
 * @author makejava
 * @since 2023-06-02 20:19:48
 */
@Service("bicycleinfoService")
public class BicycleInfoServiceImpl extends ServiceImpl<BicycleInfoMapper, BicycleInfo> implements BicycleInfoService {

}
