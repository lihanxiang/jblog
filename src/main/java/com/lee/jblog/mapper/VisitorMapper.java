package com.lee.jblog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VisitorMapper {

    @Select("SELECT visitor_count FROM visitor WHERE page_name = #{pageName}")
    long getVisitorCountByPageName(String pageName);

    @Select("SELECT visitor_count from visitor where page_name = 'totalVisitor'")
    long getVisitorCount();

    @Insert("INSERT INTO visitor (visitor_count, page_name)" +
            "VALUES (0, #{page_name})")
    void insertVisitorArticlePage(String pageName);

    @Update("UPDATE visitor SET visitor_count = " +
            "CASE page_name" +
            "WHEN 'totalVisitor' THEN visitor_count + 1" +
            "WHEN #{page_name} THEN visitor_count + 1" +
            "ELSE visitor_count" +
            "END")
    void updateVisitorCountByTotalVisitorAndPageName(String pageName);

    @Update("UPDATE visitor SET visitor_count = visitor_count + 1" +
            "WHERE page_name = 'totalVisitor'")
    void updateVisitorCountByTotalVisitor();
}
