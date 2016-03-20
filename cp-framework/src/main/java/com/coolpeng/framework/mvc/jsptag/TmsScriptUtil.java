package com.coolpeng.framework.mvc.jsptag;

import com.coolpeng.framework.mvc.TmsCurrentRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TmsScriptUtil {
    private static final long serialVersionUID = 1L;
    private static final String TMS_APPLICATION_CONTEXT = "TMS_APPLICATION_CONTEXT";
    private static final long START_TIME_MILLIS = System.currentTimeMillis();

    public static String toJsTag(String path) {
        String ctx = getCtx();

        return "<script type='text/javascript' src='" + ctx + path + getVersion() + "' ></script>";
    }

    public static String toCssTag(String path) {
        String ctx = getCtx();

        return "<link rel=\"stylesheet\" type=\"text/css\" href='" + ctx + path + getVersion() + "' />";
    }

    public static String getCtx() {
        return TmsCurrentRequest.getContext();
    }

    public static String getVersion() {
        return "?_version=" + getVersionValue();
    }

    public static String getVersionValue() {
        String version = "";
        return version + "_" + START_TIME_MILLIS;
    }

    public static String getPageTitle(String name) {
        return name + "";
    }

    public static String getGlobalConstScript(List<String> jsConstItems) {
        StringBuffer sb = new StringBuffer();

        sb.append("\n <script type='text/javascript'>\n");

        List<String> mm = getMoreGlobalConst();
        if ((mm != null) && (!mm.isEmpty())) {
            for (String jsConst : mm) {
                sb.append(jsConst + "\n");
            }

        }

        if ((jsConstItems != null) && (!jsConstItems.isEmpty())) {
            for (String jsConst : jsConstItems) {
                sb.append(jsConst + "\n");
            }
        }

        sb.append("</script>\n\n\n");

        return sb.toString();
    }

    private static List<String> getMoreGlobalConst() {
        List jsConstItems = new ArrayList();

        jsConstItems.add(toGlobalStringConst("APPLICATION_CONTEXT", getCtx()));

        jsConstItems.add(toGlobalStringConst("TMS_VIEW_PAGE_VERSION", getVersionValue()));

        Date d = new Date();
        String year = new SimpleDateFormat("yyyy").format(d);
        String month = new SimpleDateFormat("MM").format(d);
        String day = new SimpleDateFormat("dd").format(d);

        jsConstItems.add(toGlobalStringConst("TMS_VIEW_PAGE_TIME_YEAR", year));
        jsConstItems.add(toGlobalStringConst("TMS_VIEW_PAGE_TIME_MONTH", month));
        jsConstItems.add(toGlobalStringConst("TMS_VIEW_PAGE_TIME_DAY", day));

        return jsConstItems;
    }

    protected static String toGlobalStringConst(String constName, String constValue) {
        return " var " + constName + "='" + constValue + "';  ";
    }

    protected String toGlobalConst(String constName, String constValue) {
        return " var " + constName + "=" + constValue + ";  ";
    }
}