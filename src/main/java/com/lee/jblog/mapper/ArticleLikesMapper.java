package com.lee.jblog.mapper;

import com.lee.jblog.pojo.ArticleLikes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleLikesMapper {

    @Select("SELECT like_date FROM article_likes WHERE article_id = #{articleID} AND liker_id = #{likerID}")
    ArticleLikes isLiked(@Param("articleID") long articleID, @Param("likerID") int likerID);

    @Insert("INSERT INTO article_likes (article_id, liker_id, like_date)" +
            "VALUES (#{articleID}, #{likerID}, #{likeDate})")
    void addArticleLikes(ArticleLikes articleLikes);

    @Delete("DELETE FROM article_likes WHERE article_id = #{articleID}")
    void deleteArticleLikesByArticleID(long articleID);
}
