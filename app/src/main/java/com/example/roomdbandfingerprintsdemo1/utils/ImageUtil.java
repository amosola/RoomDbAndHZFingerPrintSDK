package com.example.roomdbandfingerprintsdemo1.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
    public static Bitmap convertBase64ToBitmap(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    public static String convertBitmapToBase64(Bitmap bitmap)
    {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;

        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);*/
    }
    public static Uri saveImage(Bitmap finalBitmap, String category, String fi_name, Activity safetyActivity) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir=null;
        Uri uri = null;
        if (category.equalsIgnoreCase("fuel")){
            myDir = new File(root + "/.secu/photos/fuel/");

        }else if (category.equalsIgnoreCase("lap")){

            myDir = new File(root + "/.secu/photos/staff/");

        }else if (category.equalsIgnoreCase("safety")){

            myDir = new File(root + "/.secu/photos/incidence/");

        }else {
            myDir = new File(root + "/.secu/photos/vehicles/");

        }
        myDir.mkdirs();

        File file = new File(myDir, fi_name);
        if (file.exists())
            file.delete ();
        try {

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            uri = FileProvider.getUriForFile(safetyActivity.getApplicationContext(),"com.capture.safetycapture" +".provider",file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }


    public static Bitmap getImageFileFromSDCard(String cate,String filename){
        Bitmap bitmap = null;
        File imageFile=null;
        if (cate.equalsIgnoreCase("fuel")){
            imageFile = new File(Environment.getExternalStorageDirectory() + "/.secu/photosfuel/"+filename);

        }else if (cate.equalsIgnoreCase("lap")){
            imageFile = new File(Environment.getExternalStorageDirectory() + "/.secu/photos/staff/"+filename);

        }else if (cate.equalsIgnoreCase("safety")){
            imageFile = new File(Environment.getExternalStorageDirectory() + "/.secu/photos/incidence/"+filename);

        }else {
            imageFile = new File(Environment.getExternalStorageDirectory() + "/.secu/photos/vehicles/"+filename);

        }
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            return null;
        }
        return bitmap;
    }

    public static Bitmap getDownsampledBitmap(Context ctx, Uri uri, int targetWidth, int targetHeight) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options outDimens = getBitmapDimensions(ctx,uri);

            int sampleSize = calculateSampleSize(outDimens.outWidth, outDimens.outHeight, targetWidth, targetHeight);

            bitmap = downsampleBitmap(ctx,uri, sampleSize);

        } catch (Exception e) {
            //handle the exception(s)
        }

        return bitmap;
    }

    private static BitmapFactory.Options getBitmapDimensions(Context cont,Uri uri) throws FileNotFoundException, IOException {
        BitmapFactory.Options outDimens = new BitmapFactory.Options();
        outDimens.inJustDecodeBounds = true; // the decoder will return null (no bitmap)

        InputStream is= cont.getContentResolver().openInputStream(uri);
        // if Options requested only the size will be returned
        BitmapFactory.decodeStream(is, null, outDimens);
        is.close();

        return outDimens;
    }

    private static int calculateSampleSize(int width, int height, int targetWidth, int targetHeight) {
        int inSampleSize = 1;

        if (height > targetHeight || width > targetWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) targetHeight);
            final int widthRatio = Math.round((float) width / (float) targetWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    private static Bitmap downsampleBitmap(Context cont,Uri uri, int sampleSize) throws FileNotFoundException, IOException {
        Bitmap resizedBitmap;
        BitmapFactory.Options outBitmap = new BitmapFactory.Options();
        outBitmap.inJustDecodeBounds = false; // the decoder will return a bitmap
        outBitmap.inSampleSize = sampleSize;

        InputStream is = cont.getContentResolver().openInputStream(uri);
        resizedBitmap = BitmapFactory.decodeStream(is, null, outBitmap);
        is.close();
        return resizedBitmap;
    }
    /*
    public static Uri saveImageReturnUri(Bitmap finalBitmap, SafetyActivity safetyActivity) {
        File imageFolder = new File(safetyActivity.getCacheDir(), "images");
        Uri uri2 = null;
        try {
            imageFolder.mkdirs();
            File file = new File(imageFolder, "captured_image.jpg");
            FileOutputStream stream = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
            uri2 = FileProvider.getUriForFile(safetyActivity.getApplicationContext(),"com.capture.safetycapture" +".provider",file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uri2;
    }
    */
}
