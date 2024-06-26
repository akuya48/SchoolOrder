package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
    void deleteBatch(List<Long> ids);
    DishDTO getById(Long id);
    void update(DishDTO dishDTO);

    void startOrStop(Integer status,Long id);
}
