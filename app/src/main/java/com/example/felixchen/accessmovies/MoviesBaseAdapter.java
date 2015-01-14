package com.example.felixchen.accessmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    public static class MovieData {
        String title;       // movie title
        String year;        // movie year
        String ratings;     // movie ratings
        String thumbnail;   // movie thumbnail
    }

    public MoviesBaseAdapter(Context context, ArrayList<MovieData> dataList) {
        mContext = context;
        mDataList = dataList;
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
        // return hashcode
        return mDataList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = (LinearLayout) inflater.inflate(R.layout.movie_item, null);
            // append title
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.append(mDataList.get(position).title);

            // append year
            TextView year = (TextView) convertView.findViewById(R.id.year);
            year.append(mDataList.get(position).year);

            // append ratings
            TextView ratings = (TextView) convertView.findViewById(R.id.ratings);
            ratings.append(mDataList.get(position).ratings);
        }

        return convertView;
    }
}
