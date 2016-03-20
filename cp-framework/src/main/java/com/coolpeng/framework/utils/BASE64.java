package com.coolpeng.framework.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class BASE64 {
	
	private static String ENCODE = "utf-8";
	
	public static void main(String[] args) {

		System.out.println(encode("朱凤珠".getBytes()));
		System.out.println(encode("111111".getBytes()));
	}
	
	  /**
     * 将二进制数据编码为BASE64字符串
     * @param binaryData
     * @return
     */
    public static String encode(byte[] binaryData) {
        try {
            return new String(Base64.encodeBase64(binaryData), ENCODE);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
     
    /**
     * 将BASE64字符串恢复为二进制数据
     * @param base64String
     * @return
     */
    public static byte[] decode(String base64String) {
        try {
        	//Base64.decodeBase64(base64String);
            return Base64.decodeBase64(base64String.getBytes(ENCODE));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
	
	public static String decodeString(String base64String){
		if(base64String!=null && !base64String.isEmpty()){
			Charset charset = Charset.forName(ENCODE);
			byte[] result=decode(base64String);
			String s= new String(result, charset);
			if(s==null || s.isEmpty()){
				return base64String;
			}else{
				return s;
			}
		}
		return base64String;
	}
	
	
	public static String encodeString(String str){
		Charset charset = Charset.forName(ENCODE);
		byte[] binaryData=str.getBytes(charset);
		return encode(binaryData);
	}
}
