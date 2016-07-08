package coolpeng.test;

/**
 * Created by Administrator on 2016/7/8.
 */
public class Album {
    private String name;
    private int begin;
    private int end;

    private String prefix;

    public Album() {
    }

    public static Album parse(String name) {
        String[] aaa = name.split("-");
        int begin = Integer.parseInt(aaa[1], 10);
        int end = Integer.parseInt(aaa[2], 10);
        return new Album(aaa[0],name, begin, end);
    }

    public Album(String prefix,String name, int begin, int end) {
        this.prefix = prefix;
        this.name = name;
        this.begin = begin;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
