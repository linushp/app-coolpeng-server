package com.coolpeng.framework.mvc.jsptag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class TmsPagination extends TagSupport {
    private static final int linkSize = 10;
    private int pageTotal;
    private int pageNow;
    private String pageUrl;
    private boolean withFirstLast;

    public int doStartTag()
            throws JspException {
        return 1;
    }

    public int doEndTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            TmsPaginationConvert convert = new TmsPaginationConvert(this.pageTotal, this.pageNow, this.pageUrl, this.withFirstLast);
            String html = convert.toPaginationHtml();

            out.print(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 6;
    }

    public static int getLinkSize() {
        return 10;
    }

    public int getPageTotal() {
        return this.pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageNow() {
        return this.pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public String getPageUrl() {
        return this.pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public boolean isWithFirstLast() {
        return this.withFirstLast;
    }

    public void setWithFirstLast(boolean withFirstLast) {
        this.withFirstLast = withFirstLast;
    }
}