package com.coolpeng.blog.entity;

import com.coolpeng.framework.db.BaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

import java.util.List;

/**

 CREATE TABLE `t_forum_post_image` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `forum_post_id` varchar(256) DEFAULT NULL,
 `forum_post_title` varchar(300) DEFAULT '',
 `image_name` varchar(256) DEFAULT NULL,
 `image_desc` varchar(256) DEFAULT NULL,
 `image_path` varchar(450) DEFAULT NULL,
 `persons` longtext,
 `create_time` varchar(256) DEFAULT NULL,
 `update_time` varchar(256) DEFAULT NULL,
 `create_user_id` varchar(256) DEFAULT NULL,
 `update_user_id` varchar(256) DEFAULT NULL,
 `status` int(11) DEFAULT '0',
 `create_mail` varchar(200) DEFAULT '',
 `create_nickname` varchar(200) DEFAULT '',
 `create_avatar` varchar(200) DEFAULT '',
 `create_ip_addr` varchar(200) DEFAULT '',
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



 */
public class ForumPostImage extends BaseEntity {
    public static SimpleDAO<ForumPostImage> DAO = new SimpleDAO<>(ForumPostImage.class);

    private String forumPostId;

    private String forumPostTitle;

    private String imageName;

    private String imageDesc;

    private String imagePath;

    /**
     * 图片中有哪些人
     */
    @FieldDef(jsonColumn = {List.class, String.class})
    private List<String> persons;

    public ForumPostImage() {
    }



    public String getForumPostId() {
        return forumPostId;
    }

    public void setForumPostId(String forumPostId) {
        this.forumPostId = forumPostId;
    }

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

    public List<String> getPersons() {
        return persons;
    }

    public void setPersons(List<String> persons) {
        this.persons = persons;
    }

    public String getForumPostTitle() {
        return forumPostTitle;
    }

    public void setForumPostTitle(String forumPostTitle) {
        this.forumPostTitle = forumPostTitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
