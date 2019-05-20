package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.VisitorMapper;
import com.lee.jblog.service.VisitorService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorMapper visitorMapper;

    @Autowired
    public VisitorServiceImpl(VisitorMapper visitorMapper) {
        this.visitorMapper = visitorMapper;
    }

    @Override
    public void insertVisitorArticlePage(String pageName, HttpServletRequest httpServletRequest) {
        String visitor = null;
        if (pageName.equals("visitorVolume")){
            visitor = (String)httpServletRequest.getSession().getAttribute("visitor");
            if (visitor == null){
                visitorMapper.updateVisitorCountByTotalVisitorAndPageName(pageName);
                httpServletRequest.getSession().setAttribute("visitor", "yes");
            } else {
                visitorMapper.updateVisitorCountByTotalVisitor();
            }
        }
    }

    @Override
    public JSONObject getVisitorCountByPageName(String pageName) {
        JSONObject jsonObject = new JSONObject();
        long totalVisitor = visitorMapper.getVisitorCountByPageName("totalVisitor");
        long pageVisitor;
        try {
            pageVisitor = visitorMapper.getVisitorCountByPageName(pageName);
            jsonObject.put("totalVisitor", totalVisitor);
            jsonObject.put("pageVisitor", pageVisitor);
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public long countByPageName(String pageName) {
        return visitorMapper.getVisitorCountByPageName(pageName);
    }

    @Override
    public void addVisitorArticlePage(String pageName) {
        visitorMapper.insertVisitorArticlePage(pageName);
    }

    @Override
    public long getVisitorsCount() {
        return visitorMapper.getVisitorCount();
    }
}
