package com.leo.photogram.util;

public class Script {

    public static String goBack(String msg){

        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"+msg+"');");
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
    }
}
