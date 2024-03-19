package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

public interface CategoryService {
    PageResult page(CategoryPageQueryDTO pageQueryDTO);
    void insert(CategoryDTO categoryDTO);
    void update(CategoryDTO categoryDTO);
}
