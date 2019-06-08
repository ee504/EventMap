package com.starichenkov.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeImage {

    private static final String TAG = "MyLog";

    private Uri photoURI;
    private Context context;
    private Bitmap bitmapPhoto;

    public Bitmap getBitmapPhoto() {
        return bitmapPhoto;
    }

    public ChangeImage(Context context, Uri photoURI){
        this.context = context;
        this.photoURI = photoURI;
    }

    public Uri getImage1920x1080(){
        return getImage(1920, 1080);
    }

    public Uri getImage300x300(){
        return getImage(300, 300);
    }

    private Uri getImage(int width, int height) {

        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoURI);
            Log.d(TAG, "bitmap_origin.getWidth(): " + bitmap.getWidth());
            Log.d(TAG, "bitmap_origin.getHeight(): " + bitmap.getHeight());
            Log.d(TAG, "bitmap_origin.getByteCount(): " + bitmap.getByteCount());
            Log.d(TAG, "photoURI.getPath(): " + photoURI);

            Bitmap newBitmap = decodeSampledBitmapFromResource(photoURI, width, height);
            Log.d(TAG, "bitmap_new.getWidth(): " + newBitmap.getWidth());
            Log.d(TAG, "bitmap_new.getHeight(): " + newBitmap.getHeight());
            Log.d(TAG, "bitmap_new.getByteCount(): " + newBitmap.getByteCount());

            InputStream inputStream = context.getContentResolver().openInputStream(photoURI);
            ExifInterface ei = new ExifInterface(inputStream);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Log.d(TAG, "photoURI orientation: " + orientation);

            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    newBitmap = rotateImage(newBitmap, 90);
                    Log.d(TAG, "ORIENTATION_ROTATE_90");
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    Log.d(TAG, "ORIENTATION_ROTATE_180");
                    newBitmap = rotateImage(newBitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    Log.d(TAG, "ORIENTATION_ROTATE_270");
                    newBitmap = rotateImage(newBitmap, 270);
                    break;

            }

            File newFile = createImageFile();
            setBitMapToFile(newBitmap, newFile);
            Uri newUri = Uri.fromFile(newFile);
            Log.d(TAG, "newUri.getPath(): " + newUri);

            bitmapPhoto = newBitmap;
            //context.getContentResolver().delete(photoURI, null, null);

            return newUri;

        }catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ChangeImage Error: " + ex.getMessage());
        }

        return null;

    }

    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private File createImageFile() throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_.jpg";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName);
        return image;
    }

    public void setBitMapToFile(Bitmap bitMap, File file) throws IOException {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        file.createNewFile();
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(bytes.toByteArray());
        fo.close();

    }

    public Bitmap decodeSampledBitmapFromResource(Uri uri,
                                                  int reqWidth, int reqHeight) throws IOException{

        InputStream inputStream = context.getContentResolver().openInputStream(uri);

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        //return BitmapFactory.decodeResource(res, resId, options);
        InputStream inputStream2 = context.getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream2, null, options);

    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        Log.d(TAG, "calculateInSampleSize height: " + height);
        final int width = options.outWidth;
        Log.d(TAG, "calculateInSampleSize width: " + width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "calculateInSampleSize inSampleSize: " + inSampleSize);
        return inSampleSize;
    }
}
