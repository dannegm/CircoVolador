package com.amb.circovolador.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amb.circovolador.R;
import com.amb.circovolador.adapters.ThumbPicture;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressLint("ValidFragment")
public class Proyecto extends Fragment {
    Context ctx;
    JSONArray data;
    int position;

    public Proyecto (Context _ctx, int pos, JSONArray obj) {
        ctx = _ctx;
        data = obj;
        position = pos;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout;
        layout = inflater.inflate(R.layout.fragment_proyect, container, false);

        try {
            JSONObject proyect = data.getJSONObject(position);

            String photoPath = ctx.getResources().getString(R.string.fbphoto);
            String photoID = photoPath.replaceAll("__photoID__", proyect.getString("cover_photo"));

            ImageView proyectCover = (ImageView) layout.findViewById(R.id.proyectCover);
            Picasso.with(ctx)
                    .load( photoID )
                    .placeholder(R.drawable.bg_stream)
                    .error(R.drawable.bg_stream)
                    .into(proyectCover);

            Typeface sixcaps = Typeface.createFromAsset(ctx.getAssets(), "fonts/sixcaps.ttf");
            Typeface varelaround = Typeface.createFromAsset(ctx.getAssets(), "fonts/varelaround_regular.ttf");

            TextView proyectTitle = (TextView) layout.findViewById(R.id.proyectTitle);
            proyectTitle.setText( proyect.getString("name") );
            proyectTitle.setTypeface(sixcaps);

            TextView proyectDescription = (TextView) layout.findViewById(R.id.proyectDescription);
            proyectDescription.setText(proyect.getString("description"));
            proyectDescription.setTypeface(varelaround);


            JSONArray photos = proyect.getJSONObject("photos").getJSONArray("data");
            GridView listThumbs = (GridView) layout.findViewById(R.id.proyectPhotos);
            ThumbPicture thumbs = new ThumbPicture(ctx, photos);
            listThumbs.setAdapter(thumbs);
        } catch (JSONException e) {}

        return layout;
    }
}
