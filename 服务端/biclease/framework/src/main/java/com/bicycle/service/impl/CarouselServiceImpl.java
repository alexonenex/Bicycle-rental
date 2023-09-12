package com.bicycle.service.impl;


import com.bicycle.service.CarouselService;
import com.bicycle.entity.Carousel;
import com.bicycle.mapper.CarouselMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryCarousel() {
        return carouselMapper.queryCarousel();
    }
}
