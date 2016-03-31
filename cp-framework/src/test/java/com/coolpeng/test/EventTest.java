package com.coolpeng.test;

import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.event.TMSEventBus;
import com.coolpeng.framework.event.TMSEventListener;

/**
 * Created by 栾海鹏 on 2016/3/31.
 */
public class EventTest {
    public static void main(String[] args){
        TMSEventBus.addEventListener(new TMSEventListener<AAAEvent>() {

            @Override
            public void onEvent(AAAEvent event) {
                System.out.println(event.getData());
            }

        });

        AAAEvent a1 = new AAAEvent();
        a1.setData("1111111");
        TMSEventBus.sendEvent(a1);




        TMSEvent a2 = new TMSEvent();
        a2.setData("222222222");
        TMSEventBus.sendEvent(a2);
    }
}
