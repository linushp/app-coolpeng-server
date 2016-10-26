package com.coolpeng.cloud.files.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;

/**
 *

 CREATE TABLE t_cloud_files
 (
 id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
 create_time VARCHAR(256),
 update_time VARCHAR(256),
 create_user_id VARCHAR(256),
 update_user_id VARCHAR(256),
 status INT DEFAULT 0,

 file_name VARCHAR(100),
 file_url VARCHAR(500),
 file_type VARCHAR(20),
 file_desc VARCHAR(500)
 );


 */
public class CloudFiles extends BlogBaseEntity {
    public static SimpleDAO<CloudFiles> DAO = new SimpleDAO(CloudFiles.class);

    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileDesc;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }
}
