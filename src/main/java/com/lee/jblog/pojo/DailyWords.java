package com.lee.jblog.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class DailyWords {

    private String content;

    private String mood;

    private Date publishDate;
}
