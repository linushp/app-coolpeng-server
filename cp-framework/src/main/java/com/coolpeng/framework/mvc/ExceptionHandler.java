package com.coolpeng.framework.mvc;

import com.alibaba.fastjson.JSON;
import com.coolpeng.framework.exception.TMSMsgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author 栾海鹏
 * @since on 2015/11/4.
 */
public class ExceptionHandler implements HandlerExceptionResolver {


    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);


    /**
     * 一个开关，是否将异常栈显示在弹出框中，这就是那个控制是否显示出异常信息的开关！
     */
    private static boolean isShowExceptionStack = false;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {


        //这句话的意思，是让浏览器用utf8来解析返回的数据
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");

        ModelAndView mv = new ModelAndView();

        // 设置View
        mv.setView(new AbstractView() {
            @Override
            protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
                    throws Exception {
                PrintWriter out = null;
                try {
                    String str = JSON.toJSONString(model, false);
                    out = response.getWriter();
                    out.print(str);
                    out.flush();
                } catch (IllegalStateException e) {
                    logger.error(e.getMessage());
                } catch (RuntimeException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    if (out != null) out.close();
                }
            }
        });

        ModelMap modelMap = mv.getModelMap();
        modelMap.clear();

        if (ex instanceof TMSMsgException) {
            logger.error(ex.getMessage());
            TMSMsgException tmsMessageException = (TMSMsgException) ex;

            int errorCode = tmsMessageException.getErrorCode();
            String message = tmsMessageException.getMessage();
            String desc = tmsMessageException.getDesc();

            modelMap.addAttribute("responseCode", errorCode);
            modelMap.addAttribute("responseText", message);
            modelMap.addAttribute("responseDesc", desc);

        } else {
            isShowExceptionStack = true;
            /**
             * 是否显示异常堆栈的开关
             */
            if (isShowExceptionStack) {
                //错误调用栈
                String stackTrace = getStackTrace(ex);
                modelMap.addAttribute("responseCode", 10000);
                modelMap.addAttribute("responseText", htmlEncode(stackTrace));
                // 记录错误日志
                ex.printStackTrace();
                logger.error(stackTrace);

            } else {
                modelMap.addAttribute("responseCode", 10000);
                modelMap.addAttribute("responseText", "系统内部错误");
                logger.error("", ex);
                ex.printStackTrace();
            }

            modelMap.addAttribute("responseDesc", ex.getClass().getSimpleName());
        }

        return mv;
    }

    public static String htmlEncode(String s) {
        if (s != null) {
            s = s.replaceAll("<", "&lt;");
            s = s.replaceAll(">", "&gt;");
        }
        return s;
    }

    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

}