package com.abcsoft.catalogador.services;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static String getStringFromDate(Date date){
        return sdf.format(date);
    }

    public static Date getDateFromString(String string){
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIntegerFromBoolean(Boolean bool){
        return (bool) ? 1 : 0;
    }

    public static Boolean getBooleanFromInteger(int val){
        return (val == 1) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static String ValidateBookStr(String str){
        return (str != null && !str.isEmpty()) ? str : "";
    }




//    //Convert and resize our image to 400dp for faster uploading our images to DB
//    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {
//
//        try {
//
//            // Decode image size
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);
//
//            // The new size we want to scale to
//            // final int REQUIRED_SIZE =  size;
//
//            // Find the correct scale value. It should be the power of 2.
//            int width_tmp = o.outWidth, height_tmp = o.outHeight;
//            int scale = 1;
//            while (true) {
//                if (width_tmp / 2 < REQUIRED_SIZE
//                        || height_tmp / 2 < REQUIRED_SIZE) {
//                    break;
//                }
//                width_tmp /= 2;
//                height_tmp /= 2;
//                scale *= 2;
//            }
//
//            // Decode with inSampleSize
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize = scale;
//            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static Bitmap getBitmapFromLocalFile(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }

    public static Bitmap getBitmapFromURL(String src) {

        ImageDownloadTask idt = new ImageDownloadTask();
        idt.doInBackground(src);

        return null;

        //        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            // Log exception
//            return null;
//        }
    }

    //Convert bitmap to byte array
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static byte[] getBytes(Bitmap b){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }




}
