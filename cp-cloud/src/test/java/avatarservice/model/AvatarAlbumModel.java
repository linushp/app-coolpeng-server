package avatarservice.model;

/**
 * Created by luanhaipeng on 16/7/6.
 */
public class AvatarAlbumModel {

    private String albumName;

    private String prefix;

    private int begin;

    private int end;

    private int count;

    private int indexBegin;
    private int indexEnd;


    public AvatarAlbumModel() {
    }

    public AvatarAlbumModel(String albumName,String prefix, int begin, int end) {
        this.albumName = albumName;
        this.prefix = prefix;
        this.begin = begin;
        this.end = end;
        this.count = end-begin + 1;
    }

//    public AvatarAlbumModel(String albumName,String prefix, int begin, int end, int indexBegin, int indexEnd) {
//        this.albumName = albumName;
//        this.prefix = prefix;
//        this.begin = begin;
//        this.end = end;
//        this.indexBegin = indexBegin;
//        this.indexEnd = indexEnd;
//        this.count = end-begin + 1;
//    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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

    public int getIndexBegin() {
        return indexBegin;
    }

    public void setIndexBegin(int indexBegin) {
        this.indexBegin = indexBegin;
    }

    public int getIndexEnd() {
        return indexEnd;
    }

    public void setIndexEnd(int indexEnd) {
        this.indexEnd = indexEnd;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
