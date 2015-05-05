package com.amb.circovolador.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Config;
import com.amb.circovolador.adapters.Horario;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("ValidFragment")
public class Taller extends Fragment {
    Context ctx;
    JSONArray data;
    int position;

    public Taller (Context _ctx, int pos, JSONArray obj) {
        ctx = _ctx;
        data = obj;
        position = pos;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout;
        layout = inflater.inflate(R.layout.fragment_taller, container, false);

        try {
            JSONObject taller = data.getJSONObject(position);

            ImageView imageBlur = (ImageView) layout.findViewById(R.id.bgBlur);
            Picasso.with(ctx)
                    .load( taller.getString("imgsrc_blur") )
                    .placeholder(R.drawable.bg_stream)
                    .error(R.drawable.bg_stream)
                    .into(imageBlur);

            ImageView imageTaller = (ImageView) layout.findViewById(R.id.imageTaller);
            Picasso.with(ctx)
                    .load( taller.getString("imgsrc") )
                    .placeholder(R.drawable.bg_stream)
                    .error(R.drawable.bg_stream)
                    .into(imageTaller);

            Typeface sixcaps = Typeface.createFromAsset(ctx.getAssets(), "fonts/sixcaps.ttf");
            Typeface varelaround = Typeface.createFromAsset(ctx.getAssets(), "fonts/varelaround_regular.ttf");

            TextView textName = (TextView) layout.findViewById(R.id.textName);
            textName.setText( taller.getString("title") );
            textName.setTypeface(sixcaps);

            TextView textDescription = (TextView) layout.findViewById(R.id.textDescription);
            textDescription.setText(taller.getString("title"));
            textDescription.setTypeface(varelaround);

            ListView listHorarios = (ListView) layout.findViewById(R.id.listHorarios);
            Horario horarios = new Horario(ctx, taller.getJSONArray("horario"));
            listHorarios.setAdapter(horarios);
        } catch (JSONException e) {}

        return layout;
    }
}
