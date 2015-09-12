package bpig.drawerdog.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import bpig.drawerdog.dao.ImageItem;

public class TaggedImageView extends RelativeLayout implements View.OnTouchListener {
    private static final int CLICK_RANGE = 5;
    private int mCurTagX = 0;
    private int mCurTagY = 0;
    private int mTouchViewLeft = 0;
    private int mTouchViewTop = 0;
    private View mTouchView, mClickView;

//    public TaggedImageView(Context context, int resid, List<ImageItem.Tag> tagList) {
//        super(context, null);
//        setImageAndTags(resid, tagList);
//    }

    public void setImageAndTags(int resid, List<ImageItem.Tag> tagList) {
        this.setBackgroundResource(resid);
        if (tagList != null) {
            for (ImageItem.Tag tagItem : tagList) {
                this.addTagView((int) tagItem.x, (int) tagItem.y, tagItem.text);
            }
        }
    }

    //    public TaggedImageView(Context context, Drawable background, List<ImageItem.Tag> tagList) {
//        super(context, null);
//        setImageAndTags(background, tagList);
//    }
//
    public void setImageAndTags(Drawable background, List<ImageItem.Tag> tagList) {
        this.setBackground(background);
        if (tagList != null) {
            for (ImageItem.Tag tagItem : tagList) {
                this.addTagView((int) tagItem.x, (int) tagItem.y, tagItem.text);
            }
        }
    }


    public TaggedImageView(Context context) {
        super(context, null);
    }

    public TaggedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initResources();
    }

    private void initResources() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchView = null;
                if (mClickView != null) {
                    ((ImageTagView) mClickView).setEditStatus(ImageTagView.EditStatus.ET_NORMAL);
                    mClickView = null;
                }
                mCurTagX = (int) event.getX();
                mCurTagY = (int) event.getY();
                if (hasView(mCurTagX, mCurTagY)) {
                    mTouchViewLeft = mTouchView.getLeft();
                    mTouchViewTop = mTouchView.getTop();
                } else {
                    addTagView(mCurTagX, mCurTagY, null);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouchView((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                int endX = (int) event.getX();
                int endY = (int) event.getY();
                if (mTouchView != null && Math.abs(endX - mCurTagX) < CLICK_RANGE &&
                        Math.abs(endY - mCurTagY) < CLICK_RANGE) {
                    ((ImageTagView) mTouchView).setEditStatus(ImageTagView.EditStatus.ET_EDIT);
                    mClickView = mTouchView;
                }
                mTouchView = null;
                break;
        }
        return true;
    }

    private boolean hasView(int x, int y) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            int left = (int) view.getX();
            int top = (int) view.getY();
            int right = view.getRight();
            int bottom = view.getBottom();
            Rect rect = new Rect(left, top, right, bottom);
            if (rect.contains(x, y)) {
                mTouchView = view;
                mTouchView.bringToFront();
                return true;
            }
        }
        mTouchView = null;
        return false;
    }

    private void addTagView(int x, int y, String tag) {
        View newView;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (x > getWidth() * 0.5) {
            params.leftMargin = x - ImageTagView.getViewWidth();
            newView = new ImageTagView(getContext(), ImageTagView.Direction.DIR_RIGHT, tag);
        } else {
            params.leftMargin = x;
            newView = new ImageTagView(getContext(), ImageTagView.Direction.DIR_LEFT, tag);
        }
        params.topMargin = y;
        if (params.topMargin < 0) {
            params.topMargin = 0;
        } else if ((params.topMargin + ImageTagView.getViewHeight()) > getHeight()) {
            params.topMargin = getHeight() - ImageTagView.getViewHeight();
        }

        this.addView(newView, params);
    }

    private void moveTouchView(int x, int y) {
        if (mTouchView == null) {
            return;
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x - mCurTagX + mTouchViewLeft;
        params.topMargin = y - mCurTagY + mTouchViewTop;
        if (params.leftMargin < 0 || (params.leftMargin + mTouchView.getWidth()) > getWidth()) {
            params.leftMargin = mTouchView.getLeft();
        }
        if (params.topMargin < 0 || (params.topMargin + mTouchView.getHeight()) > getHeight()) {
            params.topMargin = mTouchView.getTop();
        }
        mTouchView.setLayoutParams(params);
    }
}
