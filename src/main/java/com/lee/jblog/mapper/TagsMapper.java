package com.lee.jblog.mapper;

import com.lee.jblog.pojo.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagsMapper {

    @Select("SELECT IFNULL(MAX(id), 0) FROM tags WHERE tag_name = #{tagName}")
    int checkExistenceByName(String tagName);

    @Select("SELECT * FROM tags ORDER BY id DESC")
    List<Tag> getTags();

    @Select("SELECT COUNT(*) FROM tags")
    int getTagsCount();

    @Select("SELECT tag_size FROM tags WHERE tag_name = #{tagName}")
    int getTagsSizeByName(String tagName);

    @Insert("INSERT INTO tags (tag_name, tag_size) VALUES (#{tagName}, #{tag_size})")
    void addTags(Tag tag);
}
