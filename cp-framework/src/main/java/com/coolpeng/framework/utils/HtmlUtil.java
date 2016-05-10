package com.coolpeng.framework.utils;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.StringReader;
  
public class HtmlUtil {  

      
    public static String getTextFromHtml(String content) {
        if (content==null || content.isEmpty()){
            return "";
        }
        String txtcontent = content.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        return txtcontent;
    }

    public static String getTextFromHtml2(String content){
        if (content == null || content.isEmpty()) {
            return "";
        }

        ParseCallback callback = new ParseCallback();

        StringReader reader = new StringReader(content);

        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        try {
            delegator.parse(reader, callback, Boolean.TRUE);
            reader.close();
            return callback.getText();

        } catch (IOException e) {
            return "";
        }
    }

    public static String htmlEncode(String message){
        return StringUtils.htmlEncode(message);
    }

    private static class ParseCallback extends HTMLEditorKit.ParserCallback{
        private StringBuffer s = new StringBuffer();

        public void handleText(char[] text, int pos) {
            s.append(text);
        }

        public String getText() {
            return s.toString();
        }
    }
} 