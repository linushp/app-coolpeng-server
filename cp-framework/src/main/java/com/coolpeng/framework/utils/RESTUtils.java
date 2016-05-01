package com.coolpeng.framework.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tan Liang
 * @since 2015-07-20
 */
public class RESTUtils {
    private static Logger logger = LoggerFactory.getLogger(RESTUtils.class);


    protected static final CloseableHttpClient httpclient;
    protected static final RequestConfig globalConfig;
    protected static final RequestConfig localConfig;

    static {
        globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setRedirectsEnabled(true)
                .build();
        httpclient = HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .build();
        localConfig = RequestConfig.copy(globalConfig)
                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
                .build();
    }


    public static String get(String url0) throws IOException {



        // 创建一个 URL 对象

        URL url = new URL(url0);



        // 创建一个 URL 连接，如果有代理的话可以指定一个代理。

        URLConnection connection = url.openConnection();


        // 在开始和服务器连接之前，可能需要设置一些网络参数

        connection.setConnectTimeout(10000);

        connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)");

        // 连接到服务器

        connection.connect();



        // 往服务器写数据，数据会暂时被放到内存缓存区中

        // 如果仅是一个简单的 HTTP GET，这一部分则可以省略
//
//        OutputStream outStream = connection.getOutputStream();
//
//        ObjectOutputStream objOutput = new ObjectOutputStream(outStream);
//
//        objOutput.writeObject(new String("this is a string..."));
//
//        objOutput.flush();



        // 向服务器发送数据并获取应答

        InputStream inputStream = connection.getInputStream();

        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String text = scanner.useDelimiter("\\A").next();
        System.out.println(text);
        scanner.close();

        return text;


    }


    public static String post(String url, List<NameValuePair> nvps, String requestJsonString) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(localConfig);
//        httpPost.setHeader("Cookie", cookieStr);

        CloseableHttpResponse response = null;
        try {
            if (nvps != null) {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
                logger.trace("add url parameter: {}", nvps);
            }

            if(StringUtils.isNotBlank(requestJsonString)) {
                httpPost.setEntity(new StringEntity(requestJsonString, "UTF-8"));
                logger.trace("add request body: {}", requestJsonString);
            }

            response = httpclient.execute(httpPost);
            logger.info("{} get response status {}", url, response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            String responseJsonString = EntityUtils.toString(entity);
            logger.debug(responseJsonString);  //print JSON String
            return responseJsonString;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }
}
