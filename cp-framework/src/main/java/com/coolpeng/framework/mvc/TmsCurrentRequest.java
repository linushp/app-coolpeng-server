package com.coolpeng.framework.mvc;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TmsCurrentRequest {
    private static final ThreadLocal threadLocal = new ThreadLocal();
    private static final String TMS_CURRENT_USER = "TMS_CURRENT_USER";
    private static final String TMS_REQUEST_CONTEXT = "TMS_REQUEST_CONTEXT";

    public static void setHttpServletRequest(HttpServletRequest request) {
        threadLocal.set(request);
    }

    public static HttpServletRequest getHttpServletRequest() {
        Object request = threadLocal.get();
        if (request != null) {
            return (HttpServletRequest) request;
        }
        return null;
    }

    public static Object getSessionAttribute(String key) {
        return getHttpServletRequest().getSession().getAttribute(key);
    }

    public static void setSessionAttribute(String key, Object value) {
        getHttpServletRequest().getSession().setAttribute(key, value);
    }

    public static String getContext() {
        Object ctx = getSessionAttribute("TMS_REQUEST_CONTEXT");
        if (ctx == null) {
            HttpServletRequest request = getHttpServletRequest();

            String scheme = request.getScheme();
            String contextPath = request.getContextPath();
            String host = request.getHeader("host");

            ctx = scheme + "://" + host + contextPath;
            setSessionAttribute("TMS_REQUEST_CONTEXT", ctx);
        }
        return (String) ctx;
    }

    public static void setCurrentUser(TmsUserEntity user) {
        getHttpServletRequest().getSession().setAttribute("TMS_CURRENT_USER", user);
    }

    public static TmsUserEntity getCurrentUser() {
        Object user = getHttpServletRequest().getSession().getAttribute("TMS_CURRENT_USER");
        if (user != null) {
            return (TmsUserEntity) user;
        }
        return null;
    }

    public static String getClientIpAddr() {
        HttpServletRequest request = getHttpServletRequest();

        String ipAddress = request.getHeader("x-forwarded-for");
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
            ipAddress = request.getRemoteAddr();
            if ((ipAddress.equals("127.0.0.1")) || (ipAddress.equals("0:0:0:0:0:0:0:1"))) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        if ((ipAddress != null) && (ipAddress.length() > 15) &&
                (ipAddress.indexOf(",") > 0)) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    public static boolean isAdmin() {
        TmsUserEntity user = getCurrentUser();
        if (user != null) {
            return "admin".equalsIgnoreCase(user.getPermission());
        }
        return false;
    }

    public static boolean isLogin() {
        return getCurrentUser() != null;
    }
}