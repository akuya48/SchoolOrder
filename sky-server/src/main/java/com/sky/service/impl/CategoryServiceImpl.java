package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.CategoryTypeConstant;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.CategoryNotFoundException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public PageResult page(CategoryPageQueryDTO pageQueryDTO) {
        PageHelper.startPage(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.page(pageQueryDTO);
        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total,records);
    }

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void insert(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(StatusConstant.DISABLE);
       // category.setCreateTime(LocalDateTime.now());
        //category.setUpdateTime(LocalDateTime.now());
       // category.setCreateUser(BaseContext.getCurrentId());
       // category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.insert(category);
    }

    /**
     * 更新分类
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = categoryMapper.getById(categoryDTO.getId());
        if(category == null){
            throw new CategoryNotFoundException(MessageConstant.CATEGORY_NOT_FOUND);
        }
        BeanUtils.copyProperties(categoryDTO,category);
        //category.setUpdateUser(BaseContext.getCurrentId());
        //category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status).build();
        categoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryMapper.getById(id);
        if(category == null){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_NOT_FOUND);
        }
        if(CategoryTypeConstant.DISH_TYPE.equals(category.getType())
                && dishMapper.getCountByCategoryId(category.getId()) > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        else if(CategoryTypeConstant.SETMEAL_TYPE.equals(category.getType()) &&
                setmealMapper.getCountByCategoryId(category.getId()) > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.delete(id);
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @Override
    public List<Category> getCategoryByType(Integer type) {
        return categoryMapper.getByType(type);
    }
}
