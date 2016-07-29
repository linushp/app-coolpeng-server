package com.coolpeng.framework.mvc;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TmsWebFilter
        implements Filter {
    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        res.setContentType("text/html;charset=UTF-8");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response  = (HttpServletResponse)res;

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        TmsCurrentRequest.setHttpServletRequest(request);

        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
        response.addHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Credentials","true");

        if (!isRejectByAdmin(request, response) && !isRejectByApp(request, response)){
            chain.doFilter(request, res);
        }


    }



    private boolean isRejectByApp(ServletRequest request, HttpServletResponse response) {
        return false;
    }

    private boolean isRejectByAdmin(ServletRequest request, HttpServletResponse response)
            throws IOException {
        String uri = ((HttpServletRequest) request).getRequestURI();

        uri = uri.toLowerCase();
        if ((uri.indexOf("admin") != -1) && (uri.indexOf("/admin/login") == -1) &&
                (!TmsCurrentRequest.isAdmin())) {
            HttpServletResponse response1 = (HttpServletResponse) response;
            response1.sendRedirect(TmsCurrentRequest.getContext() + "/admin/login.shtml");
            return true;
        }

        return false;
    }

    public void destroy() {
    }
}