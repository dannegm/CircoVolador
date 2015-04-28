package com.amb.circovolador.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Config;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("ValidFragment")
public class Evento extends Fragment {
    Context ctx;
    Activity atx;
    Config config;
    JSONArray data;
    int position;

    public Evento (Context _ctx, Activity _atx, int pos, JSONArray obj) {
        ctx = _ctx;
        atx = _atx;
        config = new Config(ctx);
        data = obj;
        position = pos;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout;
        layout = inflater.inflate(R.layout.fragment_evento, container, false);

        try {
            JSONObject event = data.getJSONObject(position);

            ImageView imageEvent = (ImageView) layout.findViewById(R.id.imageEvent);
            Picasso.with(ctx)
                    .load( event.getString("picture") )
                    .placeholder(R.drawable.bg_stream)
                    .error(R.drawable.bg_stream)
                    .into(imageEvent);

            Typeface sixcaps = Typeface.createFromAsset(ctx.getAssets(), "fonts/sixcaps.ttf");
            Typeface varelaround = Typeface.createFromAsset(ctx.getAssets(), "fonts/varelaround_regular.ttf");

            TextView textEvent = (TextView) layout.findViewById(R.id.textEvent);
            textEvent.setText(event.getString("artista") );
            textEvent.setTypeface(sixcaps);

            TextView dateEvent = (TextView) layout.findViewById(R.id.dateEvent);
            dateEvent.setText( event.getString("presentacion") );
            dateEvent.setTypeface(sixcaps);

            TextView priceEvent = (TextView) layout.findViewById(R.id.priceEvent);
            priceEvent.setText( getResources().getString(R.string.price) + ": $" + event.getString("price") );
            priceEvent.setTypeface(varelaround);

            TextView textComprar = (TextView) layout.findViewById(R.id.textComprar);
            textComprar.setTypeface(varelaround);
        } catch (JSONException e) {}

        return layout;
    }
}
