package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class Feedback {

    private int ID;

    private String feedbackContent;

    private String contactInfo;

    private int personID;

    private String feedbackDate;
}
