import com.coolpeng.blog.entity.*;
import com.coolpeng.framework.db.EntityTableUtil;

public class BdTableGenerator {
	public static void main(String[] args) {
		
//		EntityTableUtil.toCreateTable(UserEntity.class);
//
//		EntityTableUtil.toCreateTable(ImageEntity.class);
//
//		EntityTableUtil.toCreateTable(DBallProfile.class);
//
//		EntityTableUtil.toCreateTable(ForumModule.class);
//
//		EntityTableUtil.toCreateTable(ForumPost.class);
//
//		EntityTableUtil.toCreateTable(ForumPostReply.class);

		EntityTableUtil.toCreateTable(SysSetting.class);
	}
}
