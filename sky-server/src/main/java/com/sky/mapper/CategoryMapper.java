package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {
    Page<Category> page(CategoryPageQueryDTO pageQueryDTO);

    /**
     * 插入数据
     * @param category
     */
    @Insert("Insert into category(type,name,sort,status,create_time,update_time,create_user,update_user)"
    + " VALUES" + " (#{type}, #{name}, #{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Category category);

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @Select("select * from category where id = #{id}")
    Category getById(Long id);

    /**
     * 更新分类
     * @param category
     */
    void update(Category category);
}
