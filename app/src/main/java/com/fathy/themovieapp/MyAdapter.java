package com.fathy.themovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.omar.themovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


class MyAdapter extends BaseAdapter {
    List<MyMovie> movies;
    Context context;

    public MyAdapter(Context context, List<MyMovie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View poster = convertView;
        MyHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            poster = inflater.inflate(R.layout.movie, parent, false);
            holder = new MyHolder(poster);
            poster.setTag(holder);
        } else {
            holder = (MyHolder) poster.getTag();
        }

        Picasso.with(context).load(movies.get(position).poster).into(holder.imageView);
        return poster;
    }

    class MyHolder {
        ImageView imageView;

        public MyHolder(View v) {
            this.imageView = (ImageView) v.findViewById(R.id.movieImageView);
        }

    }
}
