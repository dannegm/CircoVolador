package com.amb.circovolador.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Config;
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

    ViewPager eventsPager;

    Boolean openMenu = false;

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
        /**/


        LinearLayout menu = (LinearLayout) findViewById(R.id.menu);

        final ObjectAnimator anim_open = ObjectAnimator.ofFloat(menu, "translationY", 0);
        anim_open.setDuration(200);

        final ObjectAnimator anim_close = ObjectAnimator.ofFloat(menu, "translationY", -400);
        anim_close.setDuration(200);

        RippleView toggleMenu = (RippleView) findViewById(R.id.ContextMenu);
        toggleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!openMenu) {
                    anim_open.start();
                    openMenu = true;

                    YoYo.with(Techniques.SlideInDown)
                            .duration(100)
                            .playOn(findViewById(R.id.toCartelera));
                    YoYo.with(Techniques.SlideInDown)
                            .duration(200)
                            .playOn(findViewById(R.id.toTalleres));
                    YoYo.with(Techniques.SlideInDown)
                            .duration(300)
                            .playOn(findViewById(R.id.toStreaming));
                    YoYo.with(Techniques.SlideInDown)
                            .duration(400)
                            .playOn(findViewById(R.id.toProyectos));
                } else {
                    anim_close.start();
                    openMenu = false;

                    YoYo.with(Techniques.SlideOutUp)
                            .duration(100)
                            .playOn(findViewById(R.id.toCartelera));
                    YoYo.with(Techniques.SlideOutUp)
                            .duration(200)
                            .playOn(findViewById(R.id.toTalleres));
                    YoYo.with(Techniques.SlideOutUp)
                            .duration(300)
                            .playOn(findViewById(R.id.toStreaming));
                    YoYo.with(Techniques.SlideOutUp)
                            .duration(400)
                            .playOn(findViewById(R.id.toProyectos));
                }
            }
        });
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
