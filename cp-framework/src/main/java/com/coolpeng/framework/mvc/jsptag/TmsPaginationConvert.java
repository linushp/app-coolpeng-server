package com.coolpeng.framework.mvc.jsptag;

import com.coolpeng.framework.db.PageResult;

public class TmsPaginationConvert {
    private static final int linkSize = 10;
    private int pageTotal;
    private int pageNow;
    private String pageUrl;
    private boolean withFirstLast = true;

    public TmsPaginationConvert(int pageTotal, int pageNow, String pageUrl, boolean withFirstLast) {
        this.pageTotal = pageTotal;
        this.pageNow = pageNow;
        this.pageUrl = pageUrl;
        this.withFirstLast = withFirstLast;
    }

    public TmsPaginationConvert() {
    }

    public TmsPaginationConvert(PageResult pageResult, String pageUrl) {
        this(pageResult.getPageCount(), pageResult.getPageNumber(), pageUrl, true);
    }

    public void setWithFirstLast(boolean withFirstLast) {
        this.withFirstLast = withFirstLast;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    private String generateOneLink(int i) {
        StringBuffer s = new StringBuffer();

        if (i == this.pageNow) {
            s.append(" <span>");
            s.append(i);
            s.append("</span> ");
        } else {
            s.append(" <a href=\"");
            s.append(this.pageUrl);
            s.append(i);
            s.append("\">");
            s.append(i);
            s.append("</a> ");
        }

        return s.toString();
    }

    private String generateLinks(int begin, int end) {
        StringBuffer sb = new StringBuffer();
        for (int i = begin; i <= end; i++) {
            sb.append(generateOneLink(i));
        }
        return sb.toString();
    }

    private String generateSpecialLink(int i, String text) {
        StringBuffer s = new StringBuffer();
        s.append(" <a href=\"");
        s.append(this.pageUrl);
        s.append(i);
        s.append("\">");
        s.append(text);
        s.append("</a> ");
        return s.toString();
    }

    private int pageNowGroup() {
        return (this.pageNow - 1) / 10 + 1;
    }

    private int groupSize() {
        return (this.pageTotal - 1) / 10 + 1;
    }

    private boolean isNowFirstGroup() {
        return pageNowGroup() == 1;
    }

    private boolean isNowLastGroup() {
        return pageNowGroup() == groupSize();
    }

    private int getGroupBeginNoByNow() {
        return 10 * (pageNowGroup() - 1) + 1;
    }

    private int getGroupEndNoByNow() {
        if (isNowLastGroup()) {
            return this.pageTotal;
        }
        return getGroupBeginNoByNow() + 9;
    }

    private String generateNextGroupLink(int begin) {
        return generateSpecialLink(begin, "...");
    }

    private String generatePreGroupLink(int end) {
        return generateSpecialLink(end, "...");
    }

    private String generateAllLinks() {
        StringBuffer sb = new StringBuffer();

        int begin = getGroupBeginNoByNow();
        int end = getGroupEndNoByNow();
        if (this.pageTotal <= 10) {
            sb.append(generateLinks(1, this.pageTotal));
        } else if (isNowFirstGroup()) {
            sb.append(generateLinks(begin, end));
            sb.append(generateNextGroupLink(end + 1));
        } else if (isNowLastGroup()) {
            sb.append(generatePreGroupLink(begin - 1));
            sb.append(generateLinks(begin, end));
        } else {
            sb.append(generatePreGroupLink(begin - 1));
            sb.append(generateLinks(begin, end));
            sb.append(generateNextGroupLink(end + 1));
        }

        return sb.toString();
    }

    public String toPaginationHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<div class='cp-pagination'>");

        if (this.withFirstLast) {
            sb.append(generateSpecialLink(1, "首页"));
            sb.append(generateAllLinks());
            sb.append(generateSpecialLink(this.pageTotal, "尾页"));
        } else {
            sb.append(generateAllLinks());
        }

        sb.append("</div>");

        return sb.toString();
    }
}