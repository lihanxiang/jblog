package com.lee.jblog.mapper;

import com.lee.jblog.pojo.LeaveMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LeaveMessageMapper {

    @Select("SELECT * FROM leave_message WHERE page_name = #{page_Name} AND p_id = #{pID} ORDER BY id DESC")
    List<LeaveMessage> getAllLeaveMessageDESC(@Param("pageName") String pageName, @Param("pID") int pID);

    @Select("SELECT * FROM leave_message WHERE page_name = #{page_Name} AND p_id = #{pID}")
    List<LeaveMessage> getAllLeaveMessage(@Param("pageName") String pageName, @Param("pID") int pID);

    @Select("SELECT IFNULL(max(id), 0) FROM leave_message page_name = #{page_Name} AND id = #{ID} ")
    int getLikesByPageNameAndID(@Param("pageName") String pageName, @Param("ID") int ID);

    @Select("SELECT * FROM leave_message WHERE respondent_id = #{respondentID} ORDER BY id DESC")
    List<LeaveMessage> getLeaveMessages(int respondentID);

    @Select("SELECT COUNT(*) FROM leave_message WHERE respondent_id = #{respondentID} AND is_read = 1")
    int unreadCountByID(int respondentID);

    @Select("SELECT * FROM leave_message ORDER BY id DESC LIMIT 5")
    List<LeaveMessage> getFiveNewestMessages();

    @Select("SELECT COUNT(*) FROM leave_message")
    int messagesCount();

    @Insert("INSERT INTO leave_message (page_num, p_id, commenter_id, respondent_id, leave_message_date, " +
            "likes, leave_message_content, is_read" +
            "VALUES (#{pageNum}, #{pID}, #{commenterID}, #{respondentID}, #{leaveMessageDate}, #{likes}, " +
            "#{leaveMessageContent}, #{isRead})")
    void publishMessage(LeaveMessage leaveMessage);

    @Update("UPDATE leave_message SET likes = likes + 1 WHERE page_name = #{pageName} AND id = #{ID}")
    void updateLikesByPageNameAndID(@Param("pageName") String pageName, @Param("ID") int ID);

    @Update("UPDATE leave_message SET is_read = 0 where id = #{ID}")
    void readOneMessage(int ID);

    @Update("UPDATE leave_message SET is_read = 0 where respondent_id = #{respondentID}")
    void readAllMessageByRespondentID(int respondentID);
}
