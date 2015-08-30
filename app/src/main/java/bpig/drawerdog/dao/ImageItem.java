package bpig.drawerdog.dao;

import bpig.drawerdog.R;

/**
 * Created: shuai.li(286287737@qq.com)
 * Date: 2015-08-29
 * Time: 18:49
 */
public class ImageItem {
    static class Pos {
        public Pos(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double x;
        public double y;
    }

    private int imageId;
    private String timestamp;
    private Pos pos;
    private String tag;

    public ImageItem(int imageId, String timestamp, Pos pos, String tag) {
        this.imageId = imageId;
        this.timestamp = timestamp;
        this.pos = pos;
        this.tag = tag;
    }

    public static final ImageItem[] items = {
            new ImageItem(R.drawable.subway, "2015-08-26", new Pos(0.1, 0.3), "subway"),
            new ImageItem(R.drawable.home, "2015-08-23", new Pos(0.2, 0.3), "home"),
            new ImageItem(R.drawable.code, "2015-08-20", new Pos(0.2, 0.3), "coding"),
            new ImageItem(R.drawable.ma, "2015-08-11", new Pos(0.5, 0.5), "烧香")
    };

    public int getImageId() {
        return imageId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Pos getPos() {
        return pos;
    }

    public String getTag() {
        return tag;
    }
}
