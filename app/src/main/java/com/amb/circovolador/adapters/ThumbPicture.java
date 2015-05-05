package com.amb.circovolador.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amb.circovolador.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThumbPicture extends BaseAdapter {
    private Context mContext;
    private final JSONArray photos;

    public ThumbPicture (Context c, JSONArray horarios) {
        mContext = c;
        this.photos = horarios;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list = null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            try {
                list = new View(mContext);
                list = inflater.inflate(R.layout.adapter_thumb_picture, null);

                JSONObject photo = photos.getJSONObject(position);

                String phoPath = mContext.getResources().getString(R.string.fbphoto);
                String photoID = phoPath.replaceAll("__photoID__", photo.getString("id"));

                /*
                JSONArray images = photo.getJSONArray("images");
                JSONObject image = images.getJSONObject(0);
                /**/

                ImageView thumb = (ImageView) list.findViewById(R.id.imageThumb);
                Picasso.with(mContext)
                        .load( photoID )
                        .placeholder(R.drawable.bg_stream)
                        .error(R.drawable.bg_stream)
                        .into(thumb);
            } catch (JSONException e) {}

        } else {
            list = convertView;
        }
        return list;
    }
}
