package com.starichenkov.BookMarksListView;

import android.graphics.Bitmap;
import android.util.Log;

import com.squareup.picasso.Transformation;

public class CropSquareTransformation implements Transformation {

    private static final String TAG = "MyLog";

    @Override public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Log.d(TAG, "CropSquareTransformation transform():");
        Log.d(TAG, "source.getWidth(): " + source.getWidth());
        Log.d(TAG, "source.getHeight(): " + source.getHeight());
        Log.d(TAG, "size: " + size);
        Log.d(TAG, "x: " + x);
        Log.d(TAG, "y: " + y);
        Log.d(TAG, "source.getByteCount(): " + source.getByteCount());
        Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override public String key() { return "square()"; }
}
