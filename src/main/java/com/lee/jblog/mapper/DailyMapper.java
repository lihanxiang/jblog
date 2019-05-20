package com.lee.jblog.mapper;

import com.lee.jblog.pojo.DailyWords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DailyMapper {

    @Select("SELECT * FROM daily_words ORDER BY id DESC")
    List<DailyWords> getAllWords();

    @Insert("INSERT INTO daily_words (content, mood, publish_date)" +
            "VALUES (#{content}, #{mood}, #{publishDate})")
    void addDailyWords(DailyWords dailyWords);
}
