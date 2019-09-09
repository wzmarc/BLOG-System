package com.itlike.service.impl;

import com.itlike.dao.CategoryDao;
import com.itlike.domian.Category;
import com.itlike.service.CategoryService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Setter
    private CategoryDao categoryDao;
    @Override
    public void save(Category category) {
        /*调用dao*/
        System.out.println("调用service");
        categoryDao.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        //调用dao层查询所有分类
        List<Category> list = categoryDao.getAllCategory();
        return list;
    }

    @Override
    public Category getOneCategory(Integer cid) {
        //调用dao层
        Category category = categoryDao.getOneCategory(cid);
        return category;
    }

    @Override
    public void update(Category category) {
        //调用dao
        categoryDao.update(category);
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }


}
