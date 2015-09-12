package bpig.drawerdog;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import bpig.drawerdog.dao.ImageItem;
import bpig.drawerdog.utils.ImageUtils;
import bpig.drawerdog.views.TaggedImageView;

public class LabelActivity extends AppCompatActivity {
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
        /*
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
        */
        TaggedImageView taggedImageView = (TaggedImageView) findViewById(R.id.tagged_image_view);
        List<ImageItem.Tag> tags = new ArrayList<ImageItem.Tag>() {{
            add(new ImageItem.Tag(300.0, 400.0, "subway"));
            add(new ImageItem.Tag(100.0, 600.0, "coding"));
        }};

        int toWidth = getWindowManager().getDefaultDisplay().getWidth();
        int toHeight = getWindowManager().getDefaultDisplay().getHeight();
        Drawable resizedDrawable = ImageUtils.resizeDrawable(this, R.drawable.home, toWidth, toHeight);

        float density = getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int)(resizedDrawable.getIntrinsicWidth() * density),
                (int)(resizedDrawable.getIntrinsicHeight() * density));
        taggedImageView.setLayoutParams(params);
        taggedImageView.setImageAndTags(resizedDrawable, tags);
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
