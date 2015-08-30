package bpig.drawerdog.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "xxxx";
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineWidth = getMeasuredWidth();
        int lineHeight = 0;
        int lineLeft = 0;
        int lineTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int cw = child.getMeasuredWidth();
            int ch = child.getMeasuredHeight();

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            if (lineLeft + lp.leftMargin + cw + lp.rightMargin > lineWidth) {
                lineTop += lineHeight;
                lineLeft = 0;
                lineHeight = 0;
            }
            int left = lineLeft + lp.leftMargin;
            int top = lineTop + lp.topMargin;
            int right = left + cw;
            int bottom = top + ch;
            child.layout(left, top, right, bottom);
            lineLeft = right + lp.rightMargin;
            lineHeight = Math.max(lineHeight, lp.topMargin + ch + lp.bottomMargin);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int count = getChildCount();

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int maxWidth = 0;
        int maxHeight = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int cwidth = child.getMeasuredWidth();
            int cheight = child.getMeasuredHeight();

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            cwidth += lp.leftMargin + lp.rightMargin;
            cheight += lp.topMargin + lp.bottomMargin;
            if (lineWidth + cwidth > sizeWidth) {
                maxWidth = Math.max(maxWidth, lineWidth);
                maxHeight += lineHeight;
                lineWidth = cwidth;
                lineHeight = cheight;
            } else {
                lineWidth += cwidth;
                lineHeight = Math.max(lineHeight, cheight);
            }
        }
        // last view line
        maxWidth = Math.max(maxWidth, lineWidth);
        maxHeight += lineHeight;

        int w = widthMode == MeasureSpec.EXACTLY ? maxWidth : sizeWidth;
        int h = heightMode == MeasureSpec.EXACTLY ? maxHeight : sizeHeight;
        setMeasuredDimension(w, h);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
