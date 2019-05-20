package com.lee.jblog.mapper;

import com.lee.jblog.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Select("SELECT * FROM comment_record WHERE article_id = #{articleID} AND p_id = #{pID} ORDER BY ID DESC")
    List<Comment> getCommentsByArticleIDAndPID(@Param("articleID") long articleID, @Param("pID") long pID);

    @Select("SELECT * FROM comment_record WHERE article_id = #{articleID} AND p_id = #{pID}")
    List<Comment> getCommentsByArticleIDAndPIDDisordered(@Param("articleID") long articleID, @Param("pID") long pID);

    @Select("SELECT IFNULL(MAX(likes), 0) FROM comment_record WHERE article_id = #{articleID} AND id = #{ID}")
    int getLikesByArticleIDAndID(@Param("articleID") long articleID, @Param("ID") long ID);

    @Select("SELECT * FROM comment_record ORDER BY id DESC LIMIT 5")
    List<Comment> getFiveNewestComment();

    @Select("SELECT * FROM comment_record WHERE respondent_id = #{RespondentID}")
    List<Comment> getCommentByRespondentID(int RespondentID);

    @Select("SELECT * FROM comment_record WHERE is_read = 1 AND respondent_id = #{RespondentID}")
    int unReadCountByRespondentID(int RespondentID);

    @Select("SELECT COUNT(*) FROM comment_record")
    int commentCount();

    @Insert("INSERT INTO comment_record (p_id, article_id, commenter_id, respondent_id, " +
            "comment_date, likes, comment_content, is_read)" +
            "VALUES (#{pID}, #{articleID}, #{commenterID}, #{respondentID}, " +
            "#{commentDate}, #{likes}, #{commentContent}, #{isRead})")
    void addComment(Comment comment);

    @Update("UPDATE comment_record SET likes = likes + 1 WHERE article_id = #{articleID} AND id = #{ID}")
    void updateLikeByArticleIDAndID(@Param("articleID") long articleID, @Param("ID") long ID);

    @Update("UPDATE comment_record SET is_read = 0 WHERE respondent_id = #{ID}")
    void readCommentByID(int ID);

    @Update("UPDATE comment_record SET is_read = 0 WHERE respondent_id = #{RespondentID}")
    void readCommentByRespondentID(int RespondentID);

    @Delete("DELETE FROM comment_record WHERE article_id = #{articleID}")
    void deleteCommentByArticleID(long articleID);
}
