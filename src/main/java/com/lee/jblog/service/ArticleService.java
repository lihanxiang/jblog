package com.lee.jblog.service;

import com.lee.jblog.pojo.Article;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface ArticleService {

    JSONObject addArticle(Article article);

    @Transactional
    JSONObject updateArticleByID(Article article);

    JSONObject getArticleByArticleID(long articleID, String username);

    Map<String, String> getArticleTitleByArticleID(long articleID);

    JSONArray getAllArticles(String rows, String pageNum);

    void updateArticleLastOrNextId(String lastOrNext, long lastOrNextArticleId, long articleId);

    int updateLikesByArticleID(long articleID);

    JSONObject getArticleByTag(String tag, int rows, int pageNum);

    JSONObject getArticleByCategory(String category, int rows, int pageNum);

    JSONObject findArticleByArchive(String archive, int rows, int pageNum);

    JSONObject saveDraft(Article article, String[] tags, int articleGrade);

    JSONObject getArticleManagement(int rows, int pageNum);

    Article getArticleByID(int ID);

    int getArticleCountByCategory(String category);

    int getArticleCountByArchive(String archive);

    int getArticleCount();

    @Transactional
    int deleteArticle(int ID);
}
