package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

public class DBallProfile extends BlogBaseEntity {

    public static SimpleDAO<DBallProfile> DAO = new SimpleDAO<>(DBallProfile.class);

    // 人物名称
    private String personName;

    // 最大战斗力数值
    private String maxComat;

    // 文章内容
    // LongText
    @FieldDef(dbType = FieldDef.DBTYPE_LONGTEXT)
    private String articalContent;

    // 缩略图
    private String thumbImage;

    // 首次登场时间
    private String debutVersion;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getMaxComat() {
        return maxComat;
    }

    public void setMaxComat(String maxComat) {
        this.maxComat = maxComat;
    }

    public String getArticalContent() {
        return articalContent;
    }

    public void setArticalContent(String articalContent) {
        this.articalContent = articalContent;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getDebutVersion() {
        return debutVersion;
    }

    public void setDebutVersion(String debutVersion) {
        this.debutVersion = debutVersion;
    }

}
