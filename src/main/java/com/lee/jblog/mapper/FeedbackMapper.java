package com.lee.jblog.mapper;

import com.lee.jblog.pojo.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeedbackMapper {

    @Select("SELECE * FROM feedback ORDER BY id DESC")
    List<Feedback> getAllFeedback();

    @Insert("INSERT INTO feedback (feedback_content, contact_info, person_id, feedback_date)" +
            "VALUES (#{feedbackContent}, #{contactInfo}, #{personID}, #{feedbackDate})")
    void addFeedback(Feedback feedBack);
}
