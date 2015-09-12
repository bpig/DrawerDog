package bpig.drawerdog.views;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bpig.drawerdog.R;

public class ImageTagView extends RelativeLayout implements TextView.OnEditorActionListener {
    private Context mContext;
    private View mImgTagView;
    private TextView mImgTagText;
    private EditText mImgTagEdit;

    public enum EditStatus {ET_NORMAL, ET_EDIT}

    public enum Direction {DIR_LEFT, DIR_RIGHT}

    private Direction mCurDirection = Direction.DIR_LEFT;
    private InputMethodManager mInputMethod;
    private static final int VIEW_WIDTH = 80;
    private static final int VIEW_HEIGHT = 50;

    public ImageTagView(Context context, Direction direction, String tag) {
        super(context);
        this.mContext = context;
        this.mCurDirection = direction;
        layoutViews();
        initResources();
        if (tag != null) {
            mImgTagText.setText(tag);
            mImgTagEdit.setText(tag);
        }
    }

    protected void layoutViews() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_imgtagview, this, true);
        mImgTagView = findViewById(R.id.id_imgtagview);
        mImgTagText = (TextView) findViewById(R.id.id_imgtagtext);
        mImgTagEdit = (EditText) findViewById(R.id.id_imgtagedit);
    }

    protected void initResources() {
        mInputMethod = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        mImgTagEdit.setOnEditorActionListener(this);
    }

    public void setEditStatus(EditStatus status) {
        switch (status) {
            case ET_NORMAL:
                mImgTagText.setVisibility(View.VISIBLE);
                mImgTagEdit.clearFocus();
                mImgTagText.setText(mImgTagEdit.getText());
                mImgTagEdit.setVisibility(View.GONE);
                mInputMethod.hideSoftInputFromWindow(mImgTagEdit.getWindowToken(), 0);
                break;
            case ET_EDIT:
                mImgTagText.setVisibility(View.GONE);
                mImgTagEdit.setVisibility(View.VISIBLE);
                mImgTagEdit.requestFocus();
                mInputMethod.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        setEditStatus(EditStatus.ET_NORMAL);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View parent = (View) getParent();
        int parentCenter = (int) (parent.getWidth() * 0.5);
        int thisCenter = (int) (l + this.getWidth() * 0.5);
        if (thisCenter <= parentCenter) {
            mCurDirection = Direction.DIR_LEFT;
        } else {
            mCurDirection = Direction.DIR_RIGHT;
        }
        changeDirection();
    }

    private void changeDirection() {
        switch (mCurDirection) {
            case DIR_LEFT:
                mImgTagView.setBackgroundResource(R.drawable.bg_imgtagview_right);
                break;
            case DIR_RIGHT:
                mImgTagView.setBackgroundResource(R.drawable.bg_imgtagview_left);
                break;
        }
    }

    public static int getViewWidth() {
        return VIEW_WIDTH;
    }

    public static int getViewHeight() {
        return VIEW_HEIGHT;
    }
}
