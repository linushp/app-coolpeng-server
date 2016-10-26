package com.coolpeng.cloud.files.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;

/**


 CREATE TABLE t_cloud_code
 (
 id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
 create_time VARCHAR(256),
 update_time VARCHAR(256),
 create_user_id VARCHAR(256),
 update_user_id VARCHAR(256),
 status INT DEFAULT 0,
 language VARCHAR(100),
 title VARCHAR(256),
 content LONGTEXT
 );




 */
public class CloudCode extends BlogBaseEntity {
    public static SimpleDAO<CloudCode> DAO = new SimpleDAO(CloudCode.class);


    private String language;
    private String title;
    private String content;



    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
