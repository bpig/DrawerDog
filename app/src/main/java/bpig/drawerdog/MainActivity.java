package bpig.drawerdog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bpig.drawerdog.adapter.ImageItemAdapter;
import bpig.drawerdog.dao.ImageItem;
import bpig.drawerdog.views.FlowLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static bpig.drawerdog.R.id.item_recycler;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "xxxx";
    private MediaCapturer mMediaCapturer;

    @Bind(R.id.act_main_drawerlayout)
    DrawerLayout mMainDrawerLayout;

    @Bind(R.id.left_nav)
    NavigationView mNavigationView;

    @Bind(R.id.right_layout_menu)
   FlowLayout mRightMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        ButterKnife.bind(this);
        mMediaCapturer = new MediaCapturer(this);
        initMainLayout();
        initLeftMenuLayout(mNavigationView);
        initRightMenuLayout();
    }

    private void initMainLayout() {
        ActionBar bar = getSupportActionBar();
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

    private void initLeftMenuLayout(NavigationView navigationView) {
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        return true;
                    }
                });
    }

    private void initRightMenuLayout() {
        List<String> tagList = new ArrayList<String>();
        tagList.add("基地");
        tagList.add("路灯");
        tagList.add("天鹅蓝");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
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
            case android.R.id.home:
            case R.id.action_left_menu: {
                if (!mMainDrawerLayout.isDrawerOpen(mNavigationView)) {
                    mMainDrawerLayout.openDrawer(mNavigationView);
                } else {
                    mMainDrawerLayout.closeDrawer(mNavigationView);
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

}
