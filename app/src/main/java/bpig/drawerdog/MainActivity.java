package bpig.drawerdog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import bpig.drawerdog.adapter.LeftItemAdapter;
import bpig.drawerdog.dao.LeftMenuItem;

import static bpig.drawerdog.R.id.item_recycler;

public class MainActivity extends Activity {

    private DrawerLayout mMainDrawerLayout;
    private RelativeLayout mLeftMenuLayout;
    private List<LeftMenuItem> mLeftItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        mMainDrawerLayout = (DrawerLayout)findViewById(R.id.act_main_drawerlayout);
//        getActionBar().hide();
        mediaCapturer = new MediaCapturer(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(item_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemAdapter adapter = new ItemAdapter(Item.items);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ItemAdapter.Listener() {
            @Override
            public void onClick(int position) {

            }
        });

        initLeftMenuLayout();
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
        if (mediaCapturer.process(itemId)) {
            return true;
        }
            case R.id.action_left_menu:
                mMainDrawerLayout.openDrawer(mLeftMenuLayout);
                return true;
        }
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mediaCapturer.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }
}
