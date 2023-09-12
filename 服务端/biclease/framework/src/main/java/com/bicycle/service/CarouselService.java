package com.bicycle.service;


import com.bicycle.entity.Carousel;

import java.util.List;

public interface CarouselService {

    /**
     * 查询前端轮播图数据
     * @return List<Carousel>
     */
    public List<Carousel> queryCarousel();
}
