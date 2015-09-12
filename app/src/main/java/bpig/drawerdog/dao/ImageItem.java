package bpig.drawerdog.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bpig.drawerdog.R;

/**
 * Created: shuai.li(286287737@qq.com)
 * Date: 2015-09-12
 * Time: 17:26
 */
public class ImageItem {
    public static List<ImageItem> sItems = new ArrayList<ImageItem>() {{
        add(new ImageItem(genDate(-1), R.drawable.subway, new ArrayList<Tag>() {{
            add(new Tag(0.1, 0.3, "subway"));
        }}));
        add(new ImageItem(genDate(-1), R.drawable.home, new ArrayList<Tag>() {{
            add(new Tag(0.1, 0.3, "home"));
        }}));
        add(new ImageItem(genDate(-3), R.drawable.code, new ArrayList<Tag>() {{
            add(new Tag(0.2, 0.3, "code"));
        }}));
        add(new ImageItem(genDate(-4), R.drawable.ma, new ArrayList<Tag>() {{
            add(new Tag(0.5, 0.5, "ma"));
        }}));
    }};

    public static Date genDate(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    public ImageItem(Date date, int image, List<Tag> tags) {
        mDate = date;
        mImage = image;
        mTags = tags;
    }

    Date mDate;
    int mImage;
    List<Tag> mTags;

    public static class Tag {
        public Tag(double x, double y, String text) {
            this.x = x;
            this.y = y;
            this.text = text;
        }

        public double x;
        public double y;
        public String text;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void addTag(Tag tag) {
        mTags.add(tag);
    }

    public Date getDate() {

        return mDate;
    }

    public int getImage() {
        return mImage;
    }

    public void delTag(Tag tag) {
        for (int i = 0; i < mTags.size(); ++i) {
            if (mTags.get(i).equals(tag)) {
                mTags.remove(i);
                break;
            }
        }
    }
}
