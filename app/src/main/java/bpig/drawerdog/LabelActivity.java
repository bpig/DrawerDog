package bpig.drawerdog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class LabelActivity extends Activity {
    private static final String TAG = "xxxx";
    public static final String IMAGE_URI = "image_uri";
    private ImageView mImageView;
    private String mImageValue;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.w(TAG, "back");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
        mImageView = (ImageView) findViewById(R.id.capture_img);
        Intent intent = getIntent();
        if (intent != null) {
            mImageValue = intent.getStringExtra(IMAGE_URI);
        } else if (savedInstanceState != null) {
            mImageValue = savedInstanceState.getString(IMAGE_URI);
        } else {
            return;
        }
        Uri image = Uri.parse(mImageValue);
        mImageView.setImageURI(image);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(IMAGE_URI, mImageValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_label, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
