package com.bicycle.mapper;


import com.bicycle.entity.Carousel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselMapper {

    /**
     * 查询前端轮播图数据
     * @return List<Carousel>
     */
    public List<Carousel> queryCarousel();
}