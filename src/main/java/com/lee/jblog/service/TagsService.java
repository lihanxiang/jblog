package com.lee.jblog.service;

import net.sf.json.JSONObject;

public interface TagsService {

    void addTags(String[] tags, int tagSize);

    JSONObject getTags();

    int getTagsCount();

    int getTagsSizeByName(String tagName);
}
