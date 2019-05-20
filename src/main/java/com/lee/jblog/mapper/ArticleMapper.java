package com.lee.jblog.mapper;

import com.lee.jblog.pojo.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {

    @Select("SELECT * FROM article WHERE id = #{ID}")
    Article getArticleByID(int ID);

    @Select("SELECT article_url FROM article WHERE id = #{ID}")
    String getArticleURLByID(int ID);

    @Select("SELECT * FROM article WHERE article_id = #{articleID}")
    Article getArticleByArticleID(long articleID);

    @Select("SELECT * FROM article ORDER BY id DESC")
    List<Article> getAllArticles();

    @Select("SELECT * FROM article WHERE article_tags = #{tag} ORDER BY id DESC")
    List<Article> getArticlesByTag(String tag);

    @Select("SELECT * FROM article WHERE article_categories = #{category} ORDER BY id DESC")
    List<Article> getArticlesByCategory(String category);

    @Select("SELECT * FROM article WHERE publish_date like '%${archive}' ORDER BY id DESC")
    List<Article> getArticlesByArchive(String archive);

    @Select("SELECT * FROM article ORDER BY id DESC LIMIT 1")
    Article getNewestArticle();

    @Select("SELECT * FROM article ORDER BY ID DESC LIMIT 1")
    Article getLastArticle();

    @Select("SELECT IFNULL(MAX(id), 0) FROM article WHERE article_id = #{articleID}")
    int getLikesByArticleID(long articleID);

    @Select("SELECT COUNT(*) from article WHERE article_categories = #{category}")
    int getArticleCountByCategory(String category);

    @Select("SELECT COUNT(*) from article WHERE publish_date like '%${archive}'")
    int getArticleCountByArchive(String archive);

    @Select("SELECT COUNT(*) FROM article")
    int getArticleCount();

    @Insert("INSERT article (article_id, author, article_title, article_content, article_tags, " +
            "article_type, article_categories, publish_date, update_date, article_url, article_tabloid, " +
            "likes, last_article_id, next_article_id)" +
            "VALUES (#{articleID}, #{author}, #{articleTitle}, #{articleContent}, #{articleTags}, " +
            "#{articleType}, #{articleCategories}, #{publishDate}, #{updateDate}, #{articleURL}, " +
            "#{articleTabloid}, #{likes}, #{lastArticleID}, #{nextArticleID})")
    void addArticle(Article article);

    @Update("UPDATE article SET article_title = #{articleTitle}, article_content = #{articleContent}, " +
            "article_tags = #{articleTags}, article_type = #{articleType}, article_categories = #{articleCategories}, " +
            "update_date = #{updateDate}, article_url = #{articleURL}, article_tabloid = #{articleTabloid} " +
            "WHERE id = #{id}")
    void updateArticle(Article article);

    @Update("UPDATE article SET likes = likes + 1 WHERE article_id = #{articleID}")
    void addArticleLikesByArticleID(long articleID);

    @Update("UPDATE article SET last_article_id = #{lastArticleID} WHERE article_id = #{articleID}")
    void updateLastArticleID(@Param("lastArticleID") long lastArticleID, @Param("articleID") long articleID);

    @Update("UPDATE article SET next_article_id = #{NextArticleID} WHERE article_id = #{articleID}")
    void updateNextArticleID(@Param("NextArticleID") long NextArticleID, @Param("articleID") long articleID);

    @Update("UPDATE article SET #{lastOrNext} = #{updateID} WHERE article_id = #{articleID}")
    void updateLastOrNextID(@Param("lastOrNext") String lastOrNext, @Param("updateID") long updateID,
                            @Param("articleID") long articleID);

    @Delete("DELETE FROM article WHERE article_id = #{articleID}")
    void deleteByArticleID(long articleID);
}
