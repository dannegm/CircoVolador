package com.amb.circovolador.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Config;
import com.amb.circovolador.Utils.Menu;
import com.amb.circovolador.fragments.Evento;
import com.andexert.library.RippleView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import org.apache.http.Header;
import org.json.JSONArray;

import me.relex.circleindicator.CircleIndicator;

public class Eventos extends FragmentActivity {
    Context ctx;
    Activity atx;
    Config config;
    Menu menu;

    ViewPager eventsPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        ctx = this;
        atx = this;
        config = new Config(ctx);

        final FragmentManager sfm = getSupportFragmentManager();

        Typeface sixcaps = Typeface.createFromAsset(getAssets(), "fonts/sixcaps.ttf");

        TextView TitleBar = (TextView) findViewById(R.id.TitleBar);
        TitleBar.setTypeface(sixcaps);

        String hostname = getResources().getString(R.string.hostname);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(hostname + "/eventos.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                eventsPager = (ViewPager) findViewById(R.id.eventsPager);
                eventsPager.setAdapter(new EventoPagerApadter(ctx, sfm, response));

                ParallaxPagerTransformer parallax = new ParallaxPagerTransformer(R.id.imageEvent);
                parallax.setSpeed(0.5f);
                eventsPager.setPageTransformer(false, parallax);

                CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
                circleIndicator.setViewPager(eventsPager);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                super.onFailure(statusCode, headers, responseString, e);
                String msg = "[" + statusCode + "|c/eventos] " + e.getMessage();
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
            }
        });

        menu = new Menu(this, this);
        menu.Navigation();
    }

    @Override
    public void onBackPressed() {
        if (menu.isOpen()) {
            menu.close();
        }
    }

    class EventoPagerApadter extends FragmentPagerAdapter {
        Context superCtx;
        JSONArray data;

        public EventoPagerApadter (Context _ctx, FragmentManager fm, JSONArray data) {
            super(fm);
            superCtx = _ctx;
            this.data = data;
        }
        public Fragment getItem (int position) {
            Evento event = new Evento (superCtx, atx, position, data);
            return event;
        }

        @Override
        public int getCount() {
            return data.length();
        }
    }
}
