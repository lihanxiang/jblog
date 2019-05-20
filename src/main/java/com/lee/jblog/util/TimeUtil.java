package com.lee.jblog.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {

    public String getFormatDateForThree(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    public String getFormatDateForFive(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(formatter);
    }

    public String getFormatDateForSix(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public LocalDate getParseDateForThree(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    public LocalDate getParseDateForSix(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDate.parse(date, formatter);
    }

    public long getLongTime(){
        Date now = new Date();
        return now.getTime() / 1000;
    }

    public String timeCovertToYear(String time){
        StringBuilder sb = new StringBuilder();
        sb.append(time.substring(0, 4));
        sb.append("年");
        sb.append(time.substring(5, 7));
        sb.append("月");
        return sb.toString();
    }

    public String yearCovertToTime(String year){
        StringBuilder sb = new StringBuilder();
        sb.append(year.substring(0, 4));
        sb.append("-");
        sb.append(year.substring(5, 7));
        return sb.toString();
    }
}
