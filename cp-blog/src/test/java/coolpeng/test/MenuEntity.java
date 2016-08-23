//package coolpeng.test;
//
//import com.coolpeng.blog.entity.ForumGroup;
//import com.coolpeng.framework.db.BaseEntity;
//import com.coolpeng.framework.db.SimpleDAO;
//import com.coolpeng.framework.db.annotation.FieldDef;
//import com.coolpeng.framework.exception.FieldNotFoundException;
//import com.coolpeng.framework.exception.ParameterErrorException;
//import com.coolpeng.framework.exception.UpdateErrorException;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by 栾海鹏 on 2016/3/26.
// * TODO 需要删除，这只是一个测试类
// */
//public class MenuEntity extends BaseEntity {
//
//    public static final SimpleDAO<MenuEntity> DAO = new SimpleDAO<>(MenuEntity.class);
//
//    private String text;
//
//    @FieldDef(jsonColumn = {ForumGroup.class})
//    private ForumGroup group1;
//
//    @FieldDef(jsonColumn = {List.class, ForumGroup.class})
//    private List<ForumGroup> group2;
//
//    @FieldDef(jsonColumn = {Map.class, ForumGroup.class})
//    private Map<String, ForumGroup> group3;
//
//    public ForumGroup getGroup1() {
//        return group1;
//    }
//
//    public void setGroup1(ForumGroup group1) {
//        this.group1 = group1;
//    }
//
//    public List<ForumGroup> getGroup2() {
//        return group2;
//    }
//
//    public void setGroup2(List<ForumGroup> group2) {
//        this.group2 = group2;
//    }
//
//    public Map<String, ForumGroup> getGroup3() {
//        return group3;
//    }
//
//    public void setGroup3(Map<String, ForumGroup> group3) {
//        this.group3 = group3;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//
//    public static void main(String args[]) throws UpdateErrorException, ParameterErrorException, FieldNotFoundException {
//        List<MenuEntity> x = DAO.findAll();
//        for (MenuEntity m : x) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("text", "bbbbbbbbbbb");
//            DAO.batchUpdateFields("id", m.getId(), map);
//        }
//    }
//
//
//}
