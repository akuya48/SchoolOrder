package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealDishMapper {

    @Delete("delete from setmeal_dish where dish_id=#{id}")
    void deleteByDishId(Long id);

    @Select("select count(id) from setmeal_dish where dish_id = #{id}")
    Integer getCountByDishId(Long id);
}
