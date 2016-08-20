package com.coolpeng.framework.mvc;

import com.coolpeng.framework.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TmsWebFilter
        implements Filter {
    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    private static String [] supportSuffixDynamic = {".shtml",".json"};

    private static String [] supportSuffixStatic = {".js",".css",".png",".jpg",".gif",".ico",".txt",".jsp"};

    private static boolean isSupporySuffixDynamic(String uri){
        for (String s:supportSuffixDynamic){
            if (uri.endsWith(s)){
                return true;
            }
        }
        return false;
    }

    private static boolean isSupporySuffixStatic(String uri){
        for (String s:supportSuffixStatic){
            if (uri.endsWith(s)){
                return true;
            }
        }
        return false;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response  = (HttpServletResponse)res;

        String uri = request.getRequestURI();
        if (!isSupporySuffixDynamic(uri) && !isSupporySuffixStatic(uri)){
//            response.getWriter().println("hello");
//            response.getWriter().flush();
//            response.getWriter().close();
//            request.getRequestDispatcher("/index.html").forward(request, response);

            response.sendRedirect("/home.shtml");
            return;
        }


        //js css png jpeg 等文件
        if (isSupporySuffixStatic(uri)){
            chain.doFilter(request, res);
            return;
        }


        //json shtml 等路径
        if(isSupporySuffixDynamic(uri)){

            res.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            TmsCurrentRequest.setHttpServletRequest(request);

            String Origin = request.getHeader("Origin");
            if (StringUtils.isNotBlank(Origin)){
                response.addHeader("Access-Control-Allow-Origin",Origin);
            }else {
                response.addHeader("Access-Control-Allow-Origin","*");
            }
            response.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, Cookie");
//        response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
            response.addHeader("Access-Control-Allow-Credentials","true");
            response.addHeader("Access-Control-Max-Age","18000");

            if (!isRejectByAdmin(request, response) && !isRejectByApp(request, response)){
                chain.doFilter(request, res);
            }
            return;
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