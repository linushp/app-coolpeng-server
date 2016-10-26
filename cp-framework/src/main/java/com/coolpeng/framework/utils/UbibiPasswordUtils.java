package com.coolpeng.framework.utils;

/**
 *
 */
public class UbibiPasswordUtils {


    /**
     * 明文转密文
     * @param plaintexts
     * @return
     */
    public static String plainToMd52(String plaintexts){
        String md51 = plainToMd51(plaintexts);
        String md52 = md51ToMd52(md51);
        return md52;
    }


    /**
     * 一级MD5转密文
     * @param md51Text
     * @return
     */
    public static String md51ToMd52(String md51Text){
        String md52 = MD5.GetMD5Code(md51Text+"MD52");
        return md52;
    }



    /**
     * 明文转md51
     * @param plaintexts 明文
     * @return
     */
    public static String plainToMd51(String plaintexts){
        String md51 = MD5.GetMD5Code(plaintexts+"MD51");
        return md51;
    }

}
