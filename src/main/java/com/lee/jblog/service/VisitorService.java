package com.lee.jblog.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface VisitorService {

    void insertVisitorArticlePage(String pageName, HttpServletRequest httpServletRequest);

    JSONObject getVisitorCountByPageName(String pageName);

    long countByPageName(String pageName);

    void addVisitorArticlePage(String pageName);

    long getVisitorsCount();
}
