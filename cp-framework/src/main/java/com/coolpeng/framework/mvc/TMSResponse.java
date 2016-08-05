package com.coolpeng.framework.mvc;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装错误对象
 */
public class TMSResponse<T> {
    // 错误代码
    private int responseCode;
    // 错误提示
    private String responseText = null;

    // 返回对象
    private T data;

    private int pageSize;
    private int pageNo;
    private int totalCount;

    //一些额外数据
    private Map<String, Object> extendData;

    public TMSResponse() {
        this(ResponseCode.SUCCESS);
    }

    public TMSResponse(ResponseCode responseCode) {
        this.responseCode = responseCode.getValue();
        this.responseText = responseCode.getText();
    }

    public TMSResponse(ResponseCode responseCode, T data) {
        this(responseCode);
        this.data = data;
    }



    public static TMSResponse success() {
        return new TMSResponse<>();
    }

    public static <T> TMSResponse success(T body,String msg) {
        TMSResponse<Object> obj = new TMSResponse<>(ResponseCode.SUCCESS);
        obj.setData(body);
        obj.setResponseText(msg);
        return obj;
    }

    public static <T> TMSResponse success(T body) {
        TMSResponse<Object> obj = new TMSResponse<>(ResponseCode.SUCCESS);
        obj.setData(body);
        return obj;
    }

    public static <T> TMSResponse error(ResponseCode responseCode, T data) {
        TMSResponse<Object> obj = new TMSResponse<>(responseCode);
        obj.setData(data);
        return obj;
    }

    public static TMSResponse error(ResponseCode responseCode) {
        TMSResponse<Object> obj = new TMSResponse<>(responseCode);
        return obj;
    }

    public static TMSResponse error(String responseText) {
        TMSResponse<Object> obj = new TMSResponse<>(ResponseCode.ERROR);
        obj.setResponseText(responseText);
        return obj;
    }

    public static TMSResponse error(int errorCode, String responseText) {
        TMSResponse<Object> obj = new TMSResponse<>(ResponseCode.ERROR);
        obj.setResponseText(responseText);
        obj.setResponseCode(errorCode);
        return obj;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    public TMSResponse<?> withoutDataInstance() {

        TMSResponse<?> body = new TMSResponse<>();
        body.setResponseCode(this.getResponseCode());
        body.setResponseText(this.getResponseText());
        body.setExtendData(this.getExtendData());

        return body;
    }


    public Map<String, Object> getExtendData() {
        return extendData;
    }

    public void setExtendData(Map<String, Object> extendData) {
        this.extendData = extendData;
    }

    public void addExtendData(String key, Object value) {
        if (this.extendData == null) {
            this.extendData = new HashMap<>();
        }
        this.extendData.put(key, value);
    }

}