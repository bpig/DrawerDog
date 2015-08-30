package bpig.drawerdog.dao;

public class LeftMenuItem {
    private int mImageView;
    private String mItemName;

    public LeftMenuItem(int imageView, String itemName) {
        this.mImageView = imageView;
        this.mItemName = itemName;
    }

    public int getItemImage() {
        return this.mImageView;
    }
    public void setItemImage(int imageView) {
        this.mImageView = imageView;
    }

    public String getItemName() {
        return this.mItemName;
    }
    public void setItemName(String itemName) {
        this.mItemName = itemName;
    }
}
