package com.coolpeng.framework.mvc.jsptag;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TmsViewTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    private String name = "";

    private String title = "";

    private String require = "";

    private String showHeader = "";

    private String showFooter = "";

    public int doStartTag()
            throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("    <meta http-equiv='X-UA-Compatible' content='IE=EDGE'>");
            out.println("    <title>" + TmsScriptUtil.getPageTitle(getTitle()) + "</title>");

            String constScript = TmsScriptUtil.getGlobalConstScript(getMoreGlobalConst());
            out.print(constScript);

            out.println("</head>");
            out.println("<body id='tmsBody'>");

            if (!"false".equals(this.showHeader)) {
                out.println("<div id='header'>");
                include("/common/jsp/header.jsp");
                out.println("</div>");
            }

            if ((this.require != null) && (!this.require.isEmpty())) {
                outRequire();
            }

            out.println("<div id='mainContent'>");
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return 1;
    }

    public int doEndTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            out.println("</div>");

            if (!"false".equals(this.showFooter)) {
                out.println("<div id='footer'>");
                include("/common/jsp/footer.jsp");
                out.println("</div>");
            }

            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 6;
    }

    private void outRequire() throws IOException {
        JspWriter out = this.pageContext.getOut();
        String[] requireArray = this.require.split(",");
        for (String r : requireArray)
            if ("ueditor".equalsIgnoreCase(r)) {
                out.print(TmsScriptUtil.toCssTag("/common/lib/umeditor/themes/default/css/umeditor.css"));
                out.print(TmsScriptUtil.toJsTag("/common/lib/umeditor/umeditor.config.js"));
                out.print(TmsScriptUtil.toJsTag("/common/lib/umeditor/umeditor.min.js"));
                out.print(TmsScriptUtil.toJsTag("/common/lib/umeditor/lang/zh-cn/zh-cn.js"));
            }
    }

    private void include(String path) {
        try {
            this.pageContext.include(path);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List<String> getMoreGlobalConst() {
        List jsConstItems = new ArrayList();

        jsConstItems.add(TmsScriptUtil.toGlobalStringConst("TMS_VIEW_PAGE_NAME", getName()));

        return jsConstItems;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequire() {
        return this.require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getShowHeader() {
        return this.showHeader;
    }

    public void setShowHeader(String showHeader) {
        this.showHeader = showHeader;
    }

    public String getShowFooter() {
        return this.showFooter;
    }

    public void setShowFooter(String showFooter) {
        this.showFooter = showFooter;
    }
}