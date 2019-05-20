package com.lee.jblog.component;

public class JavaScriptCheck {

    public static String javaScriptCheck(String message){
        int begin,end,_end;
        String newStr = "";
        begin = message.indexOf("<script");
        end = message.indexOf("</script>");
        if (begin == -1){
            return message;
        }
        while (begin != -1){
            _end = message.indexOf(">");
            newStr += message.substring(0, begin);
            newStr += "[removed]" + message.substring(_end+1,end) + "[removed]";
            message = message.substring(end+9);
            begin = message.indexOf("<script");
            end = message.indexOf("</script>");
        }
        return newStr;
    }
}
