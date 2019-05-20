package com.lee.jblog.mapper;

import com.lee.jblog.pojo.CommentLikes;
import com.lee.jblog.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentLikesMapper {

    @Select("SELECT like_date FROM comment_likes WHERE article_id = #{articleID} " +
            "AND p_id = #{pid} AND liker_id = likerID")
    Comment isLiked(@Param("article_id") long articleID, @Param("pID") long pID,
                    @Param("likerID") int likerID);

    @Insert("INSERT INTO comment_likes (article_id, p_id, liker_id, like_date)" +
            "VALUES (#{articleID}, #{pID}, #{likerID}, #{likeDate})")
    void addCommentLikes(CommentLikes commentLikes);

    @Delete("DELETE FROM comment_likes WHERE article_id = #{articleID}")
    void deleteCommentLikesByArticleID(long articleID);
}
