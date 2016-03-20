package com.coolpeng.blog.vo;

/**
 * Created by Administrator on 2015/9/30.
 */
public class SlideImage {
    private String text;
    private String imageUrl;
    private String linkUrl;

    public SlideImage() {
    }

    public SlideImage(String text, String imageUrl, String linkUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
