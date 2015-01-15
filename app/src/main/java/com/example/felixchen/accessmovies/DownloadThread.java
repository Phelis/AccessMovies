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
        // give the thread name
        setName("Download Thread");

        mImageView = imageView;
        mMovieData = data;

        // start run()
        this.start();

        // wait for this thread completion
        finishDownloading();
    }

    // return a bitmap
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public void run() {
        super.run();
        synchronized (this) {
            try {
                // retrieve a bitmap from url
                mBitmap = BitmapFactory.decodeStream((InputStream) new URL(mMovieData.url).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.notify();
        }
    }

    public void finishDownloading() {
        synchronized (this) {
            try {
                // wait for three seconds
                this.wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
