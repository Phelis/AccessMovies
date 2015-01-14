package com.example.felixchen.accessmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.felixchen.accessmovies.MoviesBaseAdapter.MovieData;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by FelixChen on 1/14/15.
 */
public class DownloadThread extends Thread {
    private ImageView mImageView;
    private MovieData mMovieData;
    private Bitmap mBitmap;

    public DownloadThread(MovieData data, ImageView imageView) {
        mImageView = imageView;
        mMovieData = data;

        // start run()
        this.start();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public void run() {
        super.run();
        synchronized (this) {
            try {
                mBitmap = BitmapFactory.decodeStream((InputStream) new URL(mMovieData.url).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.notify();
        }
    }
}
