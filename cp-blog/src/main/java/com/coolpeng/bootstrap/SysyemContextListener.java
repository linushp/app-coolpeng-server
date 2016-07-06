package com.coolpeng.bootstrap;

import com.coolpeng.app.listener.AppCacheRefreshListener;
import com.coolpeng.framework.event.TMSEventBus;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

/**
 * Created by luanhaipeng on 16/7/6.
 */
public class SysyemContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent e) {

    }

    public void contextInitialized(ServletContextEvent e) {
        TMSEventBus.addEventListener(new AppCacheRefreshListener());
    }
}