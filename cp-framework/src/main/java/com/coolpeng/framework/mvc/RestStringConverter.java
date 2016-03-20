package com.coolpeng.framework.mvc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;


public class RestStringConverter extends FastJsonHttpMessageConverter {

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {


        OutputStream out = outputMessage.getBody();

        String text;

        if (obj instanceof String) {
            text = obj.toString();
        } else {
            // 如果是debugger
            text = JSON.toJSONString(obj);
        }

        byte[] bytes = text.getBytes(getCharset());
        out.write(bytes);
        out.flush();

    }

}