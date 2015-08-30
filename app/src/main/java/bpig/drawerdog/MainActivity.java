package bpig.drawerdog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bpig.drawerdog.adapter.ImageItemAdapter;
import bpig.drawerdog.adapter.LeftItemAdapter;
import bpig.drawerdog.dao.ImageItem;
import bpig.drawerdog.dao.LeftMenuItem;
import bpig.drawerdog.views.FlowLayout;

import static bpig.drawerdog.R.id.item_recycler;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends Activity {
    private static final String TAG = "xxxx";
    private MediaCapturer mMediaCapturer;
    private DrawerLayout mMainDrawerLayout;
    private ListView mLeftMenu;
    private FlowLayout mRightMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        mMediaCapturer = new MediaCapturer(this);
        mMainDrawerLayout = (DrawerLayout) findViewById(R.id.act_main_drawerlayout);
        initMainLayout();
        initLeftMenuLayout();
        initRightMenuLayout();
    }

    private void initMainLayout() {
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(item_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ImageItemAdapter adapter = new ImageItemAdapter(ImageItem.items);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ImageItemAdapter.Listener() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    private void initLeftMenuLayout() {
        mLeftMenu = (ListView) findViewById(R.id.left_item_list);
        List<LeftMenuItem> itemList = new ArrayList<>();
        itemList.add(new LeftMenuItem(android.R.drawable.ic_menu_gallery, "首页"));
        itemList.add(new LeftMenuItem(android.R.drawable.star_big_off, "我的药箱"));
        itemList.add(new LeftMenuItem(android.R.drawable.ic_menu_manage, "设置"));
        itemList.add(new LeftMenuItem(android.R.drawable.ic_menu_close_clear_cancel, "退出登录"));

        LeftItemAdapter adapter = new LeftItemAdapter(this, itemList);
        mLeftMenu.setAdapter(adapter);
        mLeftMenu.setOnItemClickListener(new LeftDrawerItemClick());
    }

    private void initRightMenuLayout() {
        mRightMenuLayout = (FlowLayout)findViewById(R.id.right_layout_menu);

        final EditText searchText = (EditText)findViewById(R.id.right_menu_search);
        final Resources res = getResources();
        final Drawable searchIcon = res.getDrawable(android.R.drawable.ic_menu_search);
        final Drawable deleteIcon = res.getDrawable(android.R.drawable.ic_delete);
        searchText.setCompoundDrawablesWithIntrinsicBounds(null, null, searchIcon, null);
        searchText.addTextChangedListener(new TextWatcher() {
            private boolean mWasEmpty = true;

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    if (!mWasEmpty) {
                        searchText.setCompoundDrawablesWithIntrinsicBounds(null, null, searchIcon, null);
                        mWasEmpty = true;
                    }
                } else {
                    if (mWasEmpty) {
                        searchText.setCompoundDrawablesWithIntrinsicBounds(null, null, deleteIcon, null);
                        mWasEmpty = false;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // RS
            }
        });
        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        int curX = (int)event.getX();
                        if (curX > v.getWidth() - deleteIcon.getIntrinsicWidth() &&
                                !TextUtils.isEmpty(searchText.getText())) {
                            searchText.setText("");
                            int oldInpuType = searchText.getInputType();
                            searchText.setInputType(InputType.TYPE_NULL);
                            searchText.onTouchEvent(event);
                            searchText.setInputType(oldInpuType);
                            return true;
                        }
                }
                return false;
            }
        });

        List<String> tagList = new ArrayList<String>();
        tagList.add("基地"); tagList.add("路灯"); tagList.add("天鹅蓝");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        for (int i = 0; i < tagList.size(); ++i) {
            final LinearLayout llayout = (LinearLayout) LayoutInflater.from(this).
                    inflate(R.layout.item_tag_layout, null);
            TextView txtView = (TextView)llayout.findViewById(R.id.tag_item_textview);
            txtView.setText(tagList.get(i));
            llayout.setTag(i);
            mRightMenuLayout.addView(llayout, layoutParams);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (mMediaCapturer.process(itemId)) {
            return true;
        }
        switch (itemId) {
            case android.R.id.home:
            case R.id.action_left_menu: {
                if (!mMainDrawerLayout.isDrawerOpen(mLeftMenu)) {
                    mMainDrawerLayout.openDrawer(mLeftMenu);
                } else {
                    mMainDrawerLayout.closeDrawer(mLeftMenu);
                }
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mMediaCapturer.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    private class LeftDrawerItemClick implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    mMainDrawerLayout.closeDrawer(mLeftMenu);
                    break;
                default:
                    break;
            }
        }
    }
}
