package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.CategoryMapper;
import com.lee.jblog.service.ArticleService;
import com.lee.jblog.service.CategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final ArticleService articleService;

    @Autowired
    public CategoryServiceImpl(ArticleService articleService, CategoryMapper categoryMapper) {
        this.articleService = articleService;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public JSONObject getCategoryNameAndSize() {
        List<String> categoriesName = categoryMapper.getCategoriesName();
        JSONObject categoryJson;
        JSONArray categoryJsonArray = new JSONArray();
        JSONObject resultJson = new JSONObject();
        for (String categoryName :
                categoriesName) {
            categoryJson = new JSONObject();
            categoryJson.put("categoryName", categoryName);
            categoryJson.put("categorySize", articleService.getArticleCountByCategory(categoryName));
            categoryJsonArray.add(categoryJson);
        }
        resultJson.put("status", 200);
        resultJson.put("result", categoryJsonArray);
        return resultJson;
    }

    @Override
    public JSONArray getAllCategories() {
        List<String> categoryNames = categoryMapper.getCategoriesName();
        return JSONArray.fromObject(categoryNames);
    }

    @Override
    public int getCategoriesCount() {
        return categoryMapper.getCategoriesCount();
    }
}
