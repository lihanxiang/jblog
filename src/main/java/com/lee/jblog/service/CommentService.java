package com.lee.jblog.service;


import com.lee.jblog.pojo.Comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {

    @Transactional
    Comment addComment(Comment comment);

    JSONArray getCommentByArticleID(long articleID, String username);

    JSONArray getReplyByArticleIDAndPID(long articleID, long pid);

    JSONArray returnToReply(Comment comment, String commenter, String respondent);

    int updateLikesByArticleIDAndID(long articleID, long ID);

    JSONObject getUserAllComment(int rows, int pageNum, String username);

    JSONObject getFiveNewestComment(int rows, int pageNum, String username);

    int getCommentCount();

    void deleteCommentByArticleID(long articleID);

    void readOneComment(int ID);

    JSONObject readAllComment(String username);
}
