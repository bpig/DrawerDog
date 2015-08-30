package bpig.drawerdog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created: shuai.li(286287737@qq.com)
 * Date: 2015-08-30
 * Time: 11:43
 */
public class MediaCapturer {
    private static final String TAG = "xxxx";
    private static final int PREVIEW_REQUEST_CODE = 1;
    private static final int SAVE_REQUEST_CODE = 2;

    private Activity mContext;

    public MediaCapturer(Activity context) {
        this.mContext = context;
    }

    private String mPhotoPath;
    private File mPhotoFile;
    private static int PHOTO_CT = 0;

    private File filename() throws IOException {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String file = time + "_" + PHOTO_CT;
        PHOTO_CT++;
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.i(TAG, dir.toString());
        File image = File.createTempFile(file, ".jpg", dir);
        mPhotoPath = "file:" + image.getAbsolutePath();
        Log.i(TAG, mPhotoPath);
        return image;
    }

    public boolean process(int itemId) {
        switch (itemId) {
            case R.id.photo: {
                takePhoto();
                return true;
            }
        }
        return false;
    }

    public void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) == null) {
            return;
        }
        try {
            mPhotoFile = filename();
        } catch (IOException ex) {
            Toast toast = Toast.makeText(mContext.getApplicationContext(), "No SD card", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Log.w(TAG, "start photo");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        mContext.startActivityForResult(takePictureIntent, SAVE_REQUEST_CODE);
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != PREVIEW_REQUEST_CODE
                && requestCode != SAVE_REQUEST_CODE) {
            return false;
        }
        if (resultCode != Activity.RESULT_OK) {
            Log.w(TAG, "error " + resultCode);
            return true;
        }
        Log.i(TAG, "get photo " + requestCode);
        if (requestCode == PREVIEW_REQUEST_CODE) {
            Bundle extras = data.getExtras();
            Intent intent = new Intent(mContext, LabelActivity.class);
            intent.putExtras(extras);
            mContext.startActivity(intent);
        } else if (requestCode == SAVE_REQUEST_CODE) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(mPhotoFile);
            intent.setData(contentUri);
            mContext.sendBroadcast(intent);
            intent = new Intent(mContext, LabelActivity.class);
            intent.putExtra(LabelActivity.IMAGE_URI, contentUri.toString());
            mContext.startActivity(intent);
        }
        return true;
    }
}
