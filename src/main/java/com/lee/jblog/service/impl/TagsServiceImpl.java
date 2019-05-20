package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.TagsMapper;
import com.lee.jblog.pojo.Tag;
import com.lee.jblog.service.TagsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    private final TagsMapper tagsMapper;

    @Autowired
    public TagsServiceImpl(TagsMapper tagsMapper) {
        this.tagsMapper = tagsMapper;
    }

    @Override
    public void addTags(String[] tags, int tagSize) {
        for (String tag :
                tags) {
            if (tagsMapper.checkExistenceByName(tag) == 0){
                Tag t = new Tag(tag, tagSize);
                tagsMapper.addTags(t);
            }
        }
    }

    @Override
    public JSONObject getTags() {
        List<Tag> tags = tagsMapper.getTags();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("result", JSONArray.fromObject(tags));
        jsonObject.put("tagsCount", tags.size());
        return jsonObject;
    }

    @Override
    public int getTagsCount() {
        return tagsMapper.getTagsCount();
    }

    @Override
    public int getTagsSizeByName(String tagName) {
        return tagsMapper.getTagsSizeByName(tagName);
    }
}
