package com.lee.jblog.component;

import org.springframework.stereotype.Component;

@Component
public class StringAndArrayCoverter {

    public static String[] stringToArray(String str){
        String[] array = str.split(",");
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
        }
        return array;
    }

    public String arrayToString(String[] array){
        StringBuilder sb = new StringBuilder();
        for (String s :
                array) {
            if (sb.length() == 0){
                sb.append(s.trim());
            } else {
                sb.append(", ").append(s.trim());
            }
        }
        return sb.toString();
    }
}
