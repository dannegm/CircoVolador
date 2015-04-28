package com.amb.circovolador.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Config;
import com.amb.circovolador.fragments.Taller;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import org.apache.http.Header;
import org.json.JSONArray;

import me.relex.circleindicator.CircleIndicator;

public class Talleres extends FragmentActivity {
    Context ctx;
    Activity atx;
    Config config;

    ViewPager tallerePager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talleres);

        ctx = this;
        atx = this;
        config = new Config(ctx);

        final FragmentManager sfm = getSupportFragmentManager();

        Typeface sixcaps = Typeface.createFromAsset(getAssets(), "fonts/sixcaps.ttf");
        Typeface varelaround = Typeface.createFromAsset(ctx.getAssets(), "fonts/varelaround_regular.ttf");

        TextView TitleBar = (TextView) findViewById(R.id.TitleBar);
        TitleBar.setTypeface(sixcaps);

        TextView textInfo = (TextView) findViewById(R.id.textInfo);
        textInfo.setTypeface(varelaround);

        String hostname = getResources().getString(R.string.hostname);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(hostname + "/talleres.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tallerePager = (ViewPager) findViewById(R.id.tallerPager);
                tallerePager.setAdapter(new TallerPagerApadter(ctx, sfm, response));

                ParallaxPagerTransformer parallax = new ParallaxPagerTransformer(R.id.bgBlur);
                parallax.setSpeed(0.5f);
                tallerePager.setPageTransformer(false, parallax);

                CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
                circleIndicator.setViewPager(tallerePager);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                super.onFailure(statusCode, headers, responseString, e);
                String msg = "[" + statusCode + "|c/talleres] " + e.getMessage();
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    class TallerPagerApadter extends FragmentPagerAdapter {
        Context superCtx;
        JSONArray data;

        public TallerPagerApadter (Context _ctx, FragmentManager fm, JSONArray data) {
            super(fm);
            superCtx = _ctx;
            this.data = data;
        }
        public Fragment getItem (int position) {
            Taller taller = new Taller (superCtx, atx, position, data);
            return taller;
        }

        @Override
        public int getCount() {
            return data.length();
        }
    }
}
