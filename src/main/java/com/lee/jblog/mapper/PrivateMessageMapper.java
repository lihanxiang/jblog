package com.lee.jblog.mapper;

import com.lee.jblog.pojo.PrivateMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PrivateMessageMapper {

    @Select("SELECT * FROM private_message WHERE publisher_id = #{publisherID}")
    List<PrivateMessage> getMessageByPublishID(int publisherID);

    @Select("SELECT * FROM private_message")
    List<PrivateMessage> getAllMessage();

    @Insert("INSERT INTO private_message (private_message, publisher_id, replier_id, reply_content, publisher_date)" +
            "VALUES (#{privateMessage}, #{publisherID}, #{replierID}, #{replyContent}, #{publisherDate})")
    void publishPrivateMessage(PrivateMessage privateMessage);

    @Update("UPDATE private_message SET reply_content = #{replyContent}," +
            "replier_id = #{replierID} WHERE id = #{ID}")
    void relpyPrivateMessage(@Param("replyContent") String replyContent,
                             @Param("replierID") int replierID, @Param("ID") int ID);
}
