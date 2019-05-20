package com.lee.jblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.jblog.component.StringAndArrayCoverter;
import com.lee.jblog.mapper.ArticleMapper;
import com.lee.jblog.pojo.Article;
import com.lee.jblog.role.Owner;
import com.lee.jblog.service.*;
import com.lee.jblog.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService{

    private final ArticleMapper articleMapper;
    private final ArticleLikesService articleLikesService;
    private final VisitorService visitorService;
    private final ArchiveService archiveService;
    private final CommentLikesService commentLikesService;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, ArticleLikesService articleLikesService, VisitorService visitorService, ArchiveService archiveService, CommentService commentService, CommentLikesService commentLikesService) {
        this.articleMapper = articleMapper;
        this.articleLikesService = articleLikesService;
        this.visitorService = visitorService;
        this.archiveService = archiveService;
        this.commentLikesService = commentLikesService;
    }

    @Override
    public JSONObject addArticle(Article article) {
        JSONObject articleJson = new JSONObject();

        // 生成文章 URL
        try {
            if (article.getArticleURL().equals("")){
                String url = Owner.OWNER + "/article/" + article.getArticleID();
                article.setArticleURL(url);
            }
            Article lastAtricle = articleMapper.getLastArticle();
            if (lastAtricle != null){
                article.setLastArticleID(lastAtricle.getArticleID());
            }
            articleMapper.addArticle(article);
            TimeUtil timeUtil = new TimeUtil();
            String archiveName = timeUtil.timeCovertToYear(article.getPublishDate().
                    substring(0, 7));
            archiveService.addArchiveName(archiveName);
            visitorService.addVisitorArticlePage("article/" + article.getArticleID());
            if (lastAtricle != null){
                updateArticleLastOrNextId("nextArticleID", article.getArticleID(),
                        lastAtricle.getArticleID());
            }
            articleJson.put("status", 200);
            articleJson.put("articleTitle", article.getArticleTitle());
            articleJson.put("updateDate", article.getUpdateDate());
            articleJson.put("author", article.getAuthor());
            articleJson.put("articleURL", "/article/" + article.getArticleID());
            return articleJson;
        } catch (Exception e){
            articleJson.put("status", 500);
            e.printStackTrace();
            return articleJson;
        }
    }

    @Override
    public JSONObject updateArticleByID(Article article) {
        Article article1 = articleMapper.getArticleByID(article.getID());
        articleMapper.updateArticle(article1);
        JSONObject articleJson = new JSONObject();
        articleJson.put("status", 200);
        articleJson.put("articleTitle", article.getArticleTitle());
        articleJson.put("updateDate", article.getUpdateDate());
        articleJson.put("author", article.getAuthor());
        articleJson.put("articleURL", "/article/" + article.getArticleID());
        return articleJson;
    }

    @Override
    public JSONObject getArticleByArticleID(long articleID, String username) {
        Article article = articleMapper.getArticleByArticleID(articleID);
        JSONObject articleJson = new JSONObject();
        if (article != null){
            Article lastArticle = articleMapper.getArticleByArticleID(article.getLastArticleID());
            Article nextArticle = articleMapper.getArticleByArticleID(article.getNextArticleID());
            articleJson.put("status", 200);
            articleJson.put("author", article.getAuthor());
            articleJson.put("articleID", articleID);
            articleJson.put("articleTitle", article.getArticleTitle());
            articleJson.put("publishDate", article.getPublishDate());
            articleJson.put("articleContent", article.getArticleContent());
            articleJson.put("articleTags", article.getArticleTags());
            articleJson.put("articleType", article.getArticleType());
            articleJson.put("articleCategories", article.getArticleCategories());
            articleJson.put("articleURL", article.getArticleURL());
            articleJson.put("likes", article.getLikes());
            if (username == null){
                articleJson.put("isLiked", 0);
            } else {
                if (articleLikesService.isLiked(articleID, username)){
                    articleJson.put("isLiked", 1);
                } else {
                    articleJson.put("isLiked", 0);
                }
            }
            if (lastArticle != null){
                articleJson.put("lastStatus", 200);
                articleJson.put("lastArticleTitle", lastArticle.getArticleTitle());
                articleJson.put("lastArticleURL", "/article/" + lastArticle.getArticleID());
            } else {
                articleJson.put("lastStatus", 500);
                articleJson.put("lastInfo", "无");
            }
            if (nextArticle != null){
                articleJson.put("nextStatus", 200);
                articleJson.put("nextArticleTitle", nextArticle.getArticleTitle());
                articleJson.put("nextArticleURL", "/article/" + nextArticle.getArticleID());
            } else {
                articleJson.put("nextStatus", 500);
                articleJson.put("nextInfo", "无");
            }
            return articleJson;
        } else {
            articleJson.put("status", 500);
            articleJson.put("errorInfo", "获取文章信息失败");
            return articleJson;
        }
    }

    @Override
    public Map<String, String> getArticleTitleByArticleID(long articleID) {
        Article article = articleMapper.getArticleByArticleID(articleID);
        Map<String, String> articleMap = new HashMap<>();
        if (article != null){
            articleMap.put("articleTitle", article.getArticleTitle());
            articleMap.put("articleTabloid", article.getArticleTabloid());
        }
        return articleMap;
    }

    @Override
    public JSONArray getAllArticles(String rows, String pageNum) {
        int pageNo = Integer.parseInt(pageNum);
        int pageSize = Integer.parseInt(rows);
        PageHelper.startPage(pageNo, pageSize);
        List<Article> articles = articleMapper.getAllArticles();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;

        for (Article article :
                articles) {
            map = new HashMap<>();
            map.put("thisArticleUrl", "/article/" + article.getArticleID());
            map.put("articleTags", StringAndArrayCoverter.stringToArray(article.getArticleTags()));
            map.put("articleTitle", article.getArticleTitle());
            map.put("articleType", article.getArticleType());
            map.put("publishDate", article.getPublishDate());
            map.put("originalAuthor", article.getAuthor());
            map.put("articleCategories", article.getArticleCategories());
            map.put("articleTabloid", article.getArticleTabloid());
            map.put("likes", article.getLikes());
            newArticles.add(map);
        }

        JSONArray articleJsonArray = JSONArray.fromObject(newArticles);
        Map<String, Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum",pageInfo.getPageNum());
        thisPageInfo.put("pageSize",pageInfo.getPageSize());
        thisPageInfo.put("total",pageInfo.getTotal());
        thisPageInfo.put("pages",pageInfo.getPages());
        thisPageInfo.put("isFirstPage",pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage",pageInfo.isIsLastPage());
        articleJsonArray.add(thisPageInfo);
        return articleJsonArray;
    }

    @Override
    public void updateArticleLastOrNextId(String lastOrNext, long lastOrNextArticleId, long articleId) {
        if (lastOrNext.equals("lastArticleID")){
            articleMapper.updateLastArticleID(lastOrNextArticleId, articleId);
        }
        if (lastOrNext.equals("nextArticleID")){
            articleMapper.updateNextArticleID(lastOrNextArticleId, articleId);
        }
    }

    @Override
    public int updateLikesByArticleID(long articleID) {
        articleMapper.addArticleLikesByArticleID(articleID);
        return articleMapper.getLikesByArticleID(articleID);
    }

    @Override
    public JSONObject getArticleByTag(String tag, int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<Article> articles = articleMapper.getArticlesByTag(tag);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        JSONObject articleJson;
        JSONArray articleJsonArray = new JSONArray();
        for (Article article :
                articles) {
            String[] tags = StringAndArrayCoverter.stringToArray(article.getArticleTags());
            for (String t :
                    tags) {
                if (t.equals(tag)){
                    articleJson = new JSONObject();
                    articleJson.put("articleID", article.getID());
                    articleJson.put("author", article.getAuthor());
                    articleJson.put("articleTitle", article.getArticleTitle());
                    articleJson.put("articleCategories", article.getArticleCategories());
                    articleJson.put("publishDate", article.getPublishDate());
                    articleJson.put("articleTags", tags);
                    articleJsonArray.add(articleJson);
                }
            }
        }

        JSONObject pageJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        resultJson.put("status", 200);
        resultJson.put("result", articleJsonArray);
        resultJson.put("tag", tag);
        resultJson.put("pageInfo", pageJson);
        return resultJson;
    }

    @Override
    public JSONObject getArticleByCategory(String category, int rows, int pageNum) {
        List<Article> articles;
        JSONArray articleJsonArray;
        PageHelper.startPage(pageNum, rows);
        // 如果没有说明分类，就显示全部文章
        if (category.equals("")){
            articles = articleMapper.getAllArticles();
            category = "全部分类";
        } else {
            articles = articleMapper.getArticlesByCategory(category);
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        articleJsonArray = getTimeLine(articles);

        JSONObject pageJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        resultJson.put("status", 200);
        resultJson.put("result", articleJsonArray);
        resultJson.put("category", category);
        resultJson.put("pageInfo", pageJson);
        return resultJson;
    }

    @Override
    public JSONObject findArticleByArchive(String archive, int rows, int pageNum) {
        List<Article> articles;
        JSONArray articleJsonArray;
        TimeUtil timeUtil = new TimeUtil();
        String month = "hide";
        if (archive.equals("")){
            archive = timeUtil.timeCovertToYear(archive);
        }
        PageHelper.startPage(pageNum, rows);
        // 如果当前这个月没有文章发布
        if (archive.equals("")){
            articles = articleMapper.getAllArticles();
        } else {
            articles = articleMapper.getArticlesByArchive(archive);
            month = "show";
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        articleJsonArray = getTimeLine(articles);

        JSONObject pageJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        resultJson.put("status", 200);
        resultJson.put("result", articleJsonArray);
        resultJson.put("pageInfo", pageJson);
        resultJson.put("getArticleCount", articleMapper.getArticleCount());
        resultJson.put("month", month);
        return resultJson;
    }

    @Override
    public JSONObject saveDraft(Article article, String[] tags, int articleGrade) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("ID", article.getID());
        resultJson.put("articleTitle", article.getArticleTitle());
        resultJson.put("articleType", article.getArticleType());
        resultJson.put("articleCategories", article.getArticleCategories());
        resultJson.put("articleURL", article.getArticleURL());
        resultJson.put("articleContent", article.getArticleContent());
        resultJson.put("articleTags", tags);
        resultJson.put("articleGrade", articleGrade);
        return resultJson;
    }

    @Override
    public JSONObject getArticleManagement(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<Article> articles = articleMapper.getAllArticles();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        JSONArray resultJsonArray = new JSONArray();
        JSONObject resultJson = new JSONObject();
        JSONObject articleJson;
        for (Article article :
             articles) {
            articleJson = new JSONObject();
            articleJson.put("ID", article.getID());
            articleJson.put("articleID", article.getArticleID());
            articleJson.put("author", article.getAuthor());
            articleJson.put("articleTitle", article.getArticleTitle());
            articleJson.put("articleCategories", article.getArticleCategories());
            articleJson.put("publishDate", article.getPublishDate());
            String pageName = "article/" + article.getArticleID();
            articleJson.put("visitorCount", visitorService.getVisitorCountByPageName(pageName));
            resultJsonArray.add(articleJson);
        }
        resultJson.put("status", 200);
        resultJson.put("result", resultJsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        resultJson.put("pageInfo", pageJson);
        return resultJson;
    }

    @Override
    public Article getArticleByID(int ID) {
        return articleMapper.getArticleByID(ID);
    }

    @Override
    public int getArticleCountByCategory(String category) {
        return articleMapper.getArticleCountByCategory(category);
    }

    @Override
    public int getArticleCountByArchive(String archive) {
        return articleMapper.getArticleCountByArchive(archive);
    }

    @Override
    public int getArticleCount() {
        return articleMapper.getArticleCount();
    }

    @Override
    public int deleteArticle(int ID) {
        Article article = articleMapper.getArticleByID(ID);
        long articleID = article.getArticleID();
        // 把下一篇文章的上一篇设置为要删除文章的上一篇
        articleMapper.updateLastOrNextID("last_article_id", article.getLastArticleID(),
                article.getNextArticleID());
        articleMapper.updateLastOrNextID("next_article_id", article.getNextArticleID(),
                article.getLastArticleID());
        articleMapper.deleteByArticleID(articleID);
        commentLikesService.deleteALLCommentLikesByArticleID(articleID);
        articleLikesService.deleteArticleLikesByArticleID(articleID);
        return 1;
    }

    private JSONArray getTimeLine(List<Article> articles){
        JSONObject articleJson;
        JSONArray articleJsonArray = new JSONArray();
        for (Article article :
                articles) {
            String[] tags = StringAndArrayCoverter.stringToArray(article.getArticleTags());
            articleJson = new JSONObject();
            articleJson.put("articleID", article.getArticleID());
            articleJson.put("author", article.getAuthor());
            articleJson.put("articleTitle", article.getArticleTitle());
            articleJson.put("articleCategories", article.getArticleCategories());
            articleJson.put("publishTime", article.getPublishDate());
            articleJson.put("articleTags", tags);
            articleJsonArray.add(articleJson);
        }
        return articleJsonArray;
    }
}
