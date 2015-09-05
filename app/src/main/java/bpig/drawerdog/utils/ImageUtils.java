package bpig.drawerdog.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageUtils {
    public static Drawable resizeDrawable(Context context, int resid, int w, int h) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resid);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = (float)w / width;
        float scaleHeight = (float)h / height;
        float scaleMin = (scaleWidth < scaleHeight) ? scaleWidth : scaleHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleMin, scaleMin);
        Bitmap resized = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(resized);
    }
}
