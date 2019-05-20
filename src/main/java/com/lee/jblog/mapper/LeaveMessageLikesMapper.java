package com.lee.jblog.mapper;

import com.lee.jblog.pojo.LeaveMessageLikes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LeaveMessageLikesMapper {

    @Select("SELECT like_date FROM leave_message_likes WHERE page_name = #{pageName} " +
            "AND p_id = #{pID} AND liker_id = #{likerID}")
    LeaveMessageLikes isLiked(@Param("pageName") String pageName, @Param("pID") int pID,
                              @Param("likerID") int likerID);

    @Insert("INSERT INTO leave_message_likes (page_name, p_id, liker_id, like_date)" +
            "VALUES (#{pageName}, #{pID}, #{likerID}, #{likeDate})")
    void addLeaveMessageLikes(LeaveMessageLikes leaveMessageLikes);
}
