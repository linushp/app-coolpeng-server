package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleQuery;
import com.coolpeng.framework.db.annotation.FieldDef;

/*  
 DROP TABLE IF EXISTS `luan_blog`.`t_image`;
 CREATE TABLE  `luan_blog`.`t_image` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `image_desc` varchar(256) DEFAULT NULL,
 `image_name` varchar(256) DEFAULT NULL,
 `thumb_path` varchar(256) DEFAULT NULL,
 `image_path` varchar(256) DEFAULT NULL,
 `create_user_id` varchar(256) DEFAULT NULL,
 `create_time` int(11) DEFAULT NULL,
 `update_user_id` varchar(256) DEFAULT NULL,
 `update_time` int(11) DEFAULT NULL,
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
 */
public class ImageEntity extends BlogBaseEntity {

    public static SimpleQuery<ImageEntity> DAO = new SimpleQuery<>(ImageEntity.class);

    private String imageName;
    private String imageDesc;

    @FieldDef(dbType = FieldDef.DBTYPE_LONGTEXT)
    private String imageDescLong;

    private String thumbPath;
    private String imagePath;

    private String thumbFileName;
    private String imageFileName;

    private int thumbWidth;
    private int thumbHeight;

    private String person1;
    private String person2;
    private String person3;
    private String person4;
    private String person5;
    private String person6;

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getThumbFileName() {
        return thumbFileName;
    }

    public void setThumbFileName(String thumbFileName) {
        this.thumbFileName = thumbFileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public int getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(int thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public int getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(int thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public String getImageDescLong() {
        return imageDescLong;
    }

    public void setImageDescLong(String imageDescLong) {
        this.imageDescLong = imageDescLong;
    }

    public String getPerson1() {
        return person1;
    }

    public void setPerson1(String person1) {
        this.person1 = person1;
    }

    public String getPerson2() {
        return person2;
    }

    public void setPerson2(String person2) {
        this.person2 = person2;
    }

    public String getPerson3() {
        return person3;
    }

    public void setPerson3(String person3) {
        this.person3 = person3;
    }

    public String getPerson4() {
        return person4;
    }

    public void setPerson4(String person4) {
        this.person4 = person4;
    }

    public String getPerson5() {
        return person5;
    }

    public void setPerson5(String person5) {
        this.person5 = person5;
    }

    public String getPerson6() {
        return person6;
    }

    public void setPerson6(String person6) {
        this.person6 = person6;
    }

}
