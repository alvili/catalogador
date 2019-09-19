package com.abcsoft.catalogador.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.abcsoft.catalogador.model.ScanDetails;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

//    private ImageView imageView;
//    private Book book;
    private ScanDetails scan;
    private InputStream in;
    long inTime;
    long completeTime;

    public ImageDownloadTask() {
    }

    public ImageDownloadTask(ScanDetails scan) {
//        this.imageView = imageView;
//        this.book = book;
        this.scan = scan;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        Bitmap bitmap = null;

        try {
            inTime = System.currentTimeMillis();
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            in = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        completeTime = System.currentTimeMillis() - inTime;
//        imageView.setImageBitmap(bitmap);
//        book.setImage(bitmap);
        scan.getBook().getCover().setImage(bitmap);
        Log.d("***", "Duration for decode the Full Size Bitmap is " + completeTime);

    }
}
