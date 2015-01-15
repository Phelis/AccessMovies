package com.example.felixchen.accessmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by FelixChen on 1/14/15.
 */
public class MoviesBaseAdapter extends BaseAdapter {
    private final static String LOG_TAG = MoviesBaseAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<MovieData> mDataList;

    private LayoutInflater mInflater;

    public static class MovieData {
        String title;       // movie title
        String year;        // movie year
        String ratings;     // movie ratings
        String url;         // movie url
        Bitmap thumbnail;   // movie thumbnail
    }

    public MoviesBaseAdapter(Context context, ArrayList<MovieData> dataList) {
        mContext = context;
        mDataList = dataList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // return the size
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        // return an item
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // do nothing
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = (LinearLayout) mInflater.inflate(R.layout.movie_item, null);
        }
        // append title
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText("title: " + mDataList.get(position).title);

        // append year
        TextView year = (TextView) convertView.findViewById(R.id.year);
        year.setText("year: " + mDataList.get(position).year);

        // append ratings
        TextView ratings = (TextView) convertView.findViewById(R.id.ratings);
        ratings.setText("ratings: " + mDataList.get(position).ratings);

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

        DownloadThread thread = null;
        if (mDataList.get(position).thumbnail == null) {
            thread = new DownloadThread(mDataList.get(position), thumbnail);

            mDataList.get(position).thumbnail = thread.getBitmap();
        }

        thumbnail.setImageBitmap(mDataList.get(position).thumbnail);

        return convertView;
    }
}
