package com.lee.jblog.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface CategoryService {

    JSONObject getCategoryNameAndSize();

    JSONArray getAllCategories();

    int getCategoriesCount();
}
