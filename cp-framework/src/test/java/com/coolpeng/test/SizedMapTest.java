package com.coolpeng.test;

import com.coolpeng.framework.utils.data.MaxSizedMap;

import java.util.Map;

/**
 * Created by luanhaipeng on 16/10/10.
 */
public class SizedMapTest {
    public static void main(String [] args){
        Map<String,String> m = new MaxSizedMap(2);

        for (int i=0;i<10;i++){
            m.put(""+i,"ddd"+i);
        }

        System.out.println(m);
        //8,9


        Map<String,String> m2 = new MaxSizedMap(3);
        for (int i=19;i<30;i++){
            m2.put(""+i,"ddd"+i);
        }
        m2.putAll(m);

        System.out.println(m);
        //29,9,8

    }
}
