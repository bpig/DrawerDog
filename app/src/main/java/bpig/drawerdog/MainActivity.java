package bpig.drawerdog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bpig.drawerdog.adapter.ImageItemAdapter;
import bpig.drawerdog.dao.ImageItem;
import bpig.drawerdog.views.FlowLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "xxxx";
    private MediaCapturer mMediaCapturer;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.left_nav)
    NavigationView mLeftNav;

    @Bind(R.id.content)
    View mContent;

    @Bind(R.id.right_layout_menu)
    FlowLayout mRightMenuLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMediaCapturer = new MediaCapturer(this);
        initMainLayout();
        initLeftMenuLayout();
        initRightMenuLayout();
    }

    private void initMainLayout() {
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            bar.setDisplayHomeAsUpEnabled(true);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ImageItemAdapter adapter = new ImageItemAdapter(ImageItem.items);
        recyclerView.setAdapter(adapter);
        adapter.setListener(position -> {
            //todo
        });
    }

    private void initLeftMenuLayout() {
        mLeftNav.setNavigationItemSelectedListener(menuItem -> {
            Snackbar.make(mContent, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
            menuItem.setChecked(true);
            return true;
        });
    }

    private void initRightMenuLayout() {
        List<String> tagList = new ArrayList<String>() {{
            add("基地");
            add("路灯");
            add("天鹅蓝");
            add("将太无二");
        }};
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < tagList.size(); ++i) {
            final LinearLayout llayout = (LinearLayout) LayoutInflater.from(this).
                    inflate(R.layout.item_tag_layout, null);
            TextView txtView = (TextView) llayout.findViewById(R.id.tag_item_textview);
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
            case android.R.id.home: {
                mDrawerLayout.openDrawer(mLeftNav);
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

}
