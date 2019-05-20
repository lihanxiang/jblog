package com.lee.jblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {

    @Select("SELECT category_name FROM categories")
    List<String> getCategoriesName();

    @Select("SELECT COUNT(*) FROM categories")
    int getCategoriesCount();
}
