package com.example.felixchen.accessmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by FelixChen on 1/14/15.
 */
public class DownloadThread extends Thread {
    private ImageView mImageView;
    private String mURL;

    public DownloadThread(String url, ImageView imageView) {
        mImageView = imageView;
        mURL = url;
    }

    @Override
    public void run() {
        super.run();

        try{
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(mURL).getContent());
            mImageView.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
