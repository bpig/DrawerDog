package bpig.drawerdog.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FlowLayout extends ViewGroup {
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
        int maxLineWidth = getWidth();
        ArrayList<View> lineViews = new ArrayList<>();
        int lineHeight = 0;
        int lineLeft = 0;
        int lineTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int cw = child.getMeasuredWidth();
            int ch = child.getMeasuredHeight();

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int left = lp.leftMargin;
            int top = lp.topMargin;
            int right = 0;
            int bottom = 0;
            if( lineLeft + lp.leftMargin + cw + lp.rightMargin > maxLineWidth){
                lineTop += lineHeight + lp.bottomMargin;
                lineLeft = 0;
            }else{
                lineHeight  = Math.max(lineHeight, ch + lp.bottomMargin);
            }
            left += lineLeft ;
            top += lineTop;
            right = left + cw ;
            if (right > getMeasuredWidth()){
                right = getMeasuredWidth() - lp.rightMargin;
            }
            bottom = top + ch ;
            child.layout(left, top, right, bottom);
            lineLeft = right + lp.rightMargin;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
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
        for (int i = 0 ; i < count; i++){
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int cwidth = child.getMeasuredWidth();
            int cheight = child.getMeasuredHeight();

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            cwidth += lp.leftMargin+lp.rightMargin;
            cheight += lp.topMargin+lp.bottomMargin;
            if(lineWidth + cwidth > sizeWidth){
                maxWidth = Math.max(maxWidth, lineWidth);
                maxHeight += lineHeight;

                lineWidth = cwidth;
                lineHeight = cheight;
            }else{
                lineWidth += cwidth;
                lineHeight = Math.max(lineHeight, cheight);
            }
        }
        // 最后一个子View,
        maxHeight += lineHeight;
        //
        int w = widthMode == MeasureSpec.EXACTLY ? sizeWidth : maxWidth;
        int h = heightMode == MeasureSpec.EXACTLY ? sizeHeight : maxHeight;
        setMeasuredDimension(w, h);

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
