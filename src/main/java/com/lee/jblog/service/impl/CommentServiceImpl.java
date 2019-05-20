package com.lee.jblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.jblog.mapper.CommentMapper;
import com.lee.jblog.pojo.Comment;
import com.lee.jblog.pojo.UnreadNews;
import com.lee.jblog.redis.HashService;
import com.lee.jblog.service.ArticleService;
import com.lee.jblog.service.CommentLikesService;
import com.lee.jblog.service.CommentService;
import com.lee.jblog.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentLikesService commentLikesService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    HashService hashService;

    private void addUnreadNews(Comment comment){
        if (comment.getCommenterID() != comment.getRespondentID()){
            boolean isKeyExit = hashService.hasKey(String.valueOf(comment.getRespondentID()));
            if (!isKeyExit){
                UnreadNews news = new UnreadNews(1, 1, 0);
                hashService.put(String.valueOf(comment.getRespondentID()), news, UnreadNews.class);
            } else {
                hashService.hashIncrement(String.valueOf(comment.getRespondentID()), "allNewsCount", 1);
                hashService.hashIncrement(String.valueOf(comment.getRespondentID()), "commentsCount", 1);
            }
        }
    }

    @Override
    public Comment addComment(Comment comment) {
        if (comment.getCommenterID() == comment.getRespondentID()){
            comment.setIsRead(0);
        }
        commentMapper.addComment(comment);
        addUnreadNews(comment);
        return comment;
    }

    @Override
    public JSONArray getCommentByArticleID(long articleID, String username) {
        List<Comment> comments = commentMapper.getCommentsByArticleIDAndPID(articleID, 0);
        JSONArray commentJsonArray = new JSONArray();
        JSONArray replyJsonArray;
        JSONObject commentJson, replyJson;
        List<Comment> replys;

        for (Comment comment :
                comments) {
            commentJson = new JSONObject();
            replys = commentMapper.getCommentsByArticleIDAndPIDDisordered(articleID, comment.getPID());
            replyJsonArray = new JSONArray();

            // 一条评论中的所有回复
            for (Comment reply : replys){
                replyJson = new JSONObject();
                replyJson.put("ID", reply.getID());
                replyJson.put("commenter", userService.getUsernameByID(reply.getCommenterID()));
                replyJson.put("commentDate", reply.getCommentDate());
                replyJson.put("commentContent", reply.getCommentDate());
                replyJson.put("respondent", userService.getUsernameByID(reply.getRespondentID()));
                replyJsonArray.add(replyJson);
            }

            // 所有评论
            commentJson.put("ID", comment.getID());
            commentJson.put("commenter", userService.getUsernameByID(comment.getCommenterID()));
            commentJson.put("commentDate", comment.getCommentDate());
            commentJson.put("likes", comment.getLikes());
            commentJson.put("commentContent", comment.getCommentDate());
            commentJson.put("replies", replyJsonArray);

            if (username == null){
                commentJson.put("isLiked", 0);
            } else {
                if (commentLikesService.isLiked(articleID, comment.getID(), username)){
                    commentJson.put("isLiked", 0);
                } else {
                    commentJson.put("isLiked", 0);
                }
            }
            commentJsonArray.add(commentJson);
            commentJson = new JSONObject();
            commentJson.put("status", 200);
            commentJsonArray.add(commentJson);
        }
        return commentJsonArray;
    }

    @Override
    public JSONArray getReplyByArticleIDAndPID(long articleID, long pid) {
        List<Comment> comments = commentMapper.getCommentsByArticleIDAndPIDDisordered(articleID, pid);
        JSONObject commentJson;
        JSONArray commentJsonArray = new JSONArray();
        for (Comment comment :
                comments) {
            commentJson = new JSONObject();
            commentJson.put("commenter", userService.getUsernameByID(comment.getCommenterID()));
            commentJson.put("respondent", userService.getUsernameByID(comment.getRespondentID()));
            commentJson.put("commentContent", comment.getCommentContent());
            commentJson.put("commentDate", comment.getCommentDate());
            commentJsonArray.add(commentJson);
        }
        commentJson = new JSONObject();
        commentJson.put("status", 200);
        commentJsonArray.add(commentJson);
        return commentJsonArray;
    }

    @Override
    public JSONArray returnToReply(Comment comment, String commenter, String respondent) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("commenter", commenter);
        jsonObject.put("respondent", respondent);
        jsonObject.put("commentContent", comment.getCommentContent());
        jsonObject.put("commentDate", comment.getCommentDate());
        jsonArray.add(jsonObject);
        jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonArray.add(jsonObject);

        return jsonArray;
    }

    @Override
    public int updateLikesByArticleIDAndID(long articleID, long ID) {
        commentMapper.updateLikeByArticleIDAndID(articleID, ID);
        return commentMapper.getLikesByArticleIDAndID(articleID, ID);
    }

    @Override
    public JSONObject getUserAllComment(int rows, int pageNum, String username) {
        int ID = userService.getIDByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<Comment> comments = commentMapper.getCommentByRespondentID(ID);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", 200);
        JSONObject commentJson;
        JSONArray commentJsonArray = new JSONArray();
        for (Comment comment :
                comments) {
            commentJson = new JSONObject();
            commentJson.put("ID", comment.getID());
            commentJson.put("pID", comment.getPID());
            commentJson.put("articleID", comment.getArticleID());
            commentJson.put("articleTitle", articleService.getArticleTitleByArticleID(comment.getArticleID()));
            commentJson.put("commenter", userService.getUsernameByID(comment.getCommenterID()));
            commentJson.put("commentDate", comment.getCommentDate());
            commentJson.put("isRead", comment.getIsRead());
            commentJsonArray.add(commentJson);
        }
        resultJson.put("result", resultJson);
        resultJson.put("UnReadMsgCount", commentMapper.unReadCountByRespondentID(ID));

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        resultJson.put("pageInfo",pageJson);
        return resultJson;
    }

    @Override
    public JSONObject getFiveNewestComment(int rows, int pageNum, String username) {
        JSONObject resultJson = new JSONObject();
        PageHelper.startPage(pageNum, rows);
        List<Comment> comments = commentMapper.getFiveNewestComment();
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;

        for (Comment comment :
                comments) {
            jsonObject = new JSONObject();
            if (comment.getPID() != 0){
                comment.setCommentContent("@" + userService.getUsernameByID(comment.getRespondentID())
                        + " " + comment.getCommentContent());
            }
            jsonObject.put("ID", comment.getID());
            jsonObject.put("commenter", comment.getArticleID());
            jsonObject.put("articleID", userService.getUsernameByID(comment.getCommenterID()));
            jsonObject.put("commentContent", comment.getCommentContent());
            jsonObject.put("commentDate", comment.getCommentDate());
            jsonObject.put("articleTitle", articleService.getArticleTitleByArticleID(comment.getArticleID()));
            jsonArray.add(jsonObject);
        }
        resultJson.put("status", 200);
        resultJson.put("result", jsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        resultJson.put("pageInfo", pageJson);
        return resultJson;
    }

    @Override
    public int getCommentCount() {
        return commentMapper.commentCount();
    }

    @Override
    public void deleteCommentByArticleID(long articleID) {
        commentMapper.deleteCommentByArticleID(articleID);
    }

    @Override
    public void readOneComment(int ID) {
        commentMapper.readCommentByID(ID);
    }

    @Override
    public JSONObject readAllComment(String username) {
        int respondentID = userService.getIDByUsername(username);
        commentMapper.readCommentByRespondentID(respondentID);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("result", "success");
        return jsonObject;
    }
}
