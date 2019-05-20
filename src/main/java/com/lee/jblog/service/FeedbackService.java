package com.lee.jblog.service;

import com.lee.jblog.pojo.Feedback;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

public interface FeedbackService {

    @Transactional
    JSONObject submitFeedback(Feedback feedback);

    JSONObject getAllFeedback(int rows, int pageNum);
}
