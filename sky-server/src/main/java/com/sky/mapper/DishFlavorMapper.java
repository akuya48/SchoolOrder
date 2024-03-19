package com.sky.mapper;

import com.sky.entity.DishFlavor;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    void insertBatch(List<DishFlavor> flavors);
}
