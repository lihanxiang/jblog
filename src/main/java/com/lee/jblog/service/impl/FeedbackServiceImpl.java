package com.lee.jblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.jblog.mapper.FeedbackMapper;
import com.lee.jblog.pojo.Feedback;
import com.lee.jblog.service.FeedbackService;
import com.lee.jblog.service.UserService;
import com.lee.jblog.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackMapper feedbackMapper;
    private final UserService userService;

    @Autowired
    public FeedbackServiceImpl(FeedbackMapper feedbackMapper, UserService userService) {
        this.feedbackMapper = feedbackMapper;
        this.userService = userService;
    }

    @Override
    public JSONObject submitFeedback(Feedback feedback) {
        TimeUtil timeUtil = new TimeUtil();
        feedback.setFeedbackDate(timeUtil.getFormatDateForSix());
        feedbackMapper.addFeedback(feedback);
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", 200);
        return resultJson;
    }

    @Override
    public JSONObject getAllFeedback(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<Feedback> feedbacks = feedbackMapper.getAllFeedback();
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbacks);
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", 200);
        JSONArray jsonArray = new JSONArray();
        JSONObject feedbackJson;

        for (Feedback feedback :
                feedbacks) {
            feedbackJson = new JSONObject();
            feedbackJson.put("feedbackContent", feedback.getFeedbackContent());
            feedbackJson.put("username", userService.getUsernameByID(feedback.getPersonID()));
            feedbackJson.put("feedbackDate", feedback.getFeedbackDate());
            if (feedback.getContactInfo() == null){
                feedbackJson.put("contactInfo", "");
            } else {
                feedbackJson.put("contactInfo", feedback.getContactInfo());
            }
            jsonArray.add(feedbackJson);
        }

        resultJson.put("result", jsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());
        resultJson.put("pageInfo", pageJson);
        return resultJson;
    }
}
