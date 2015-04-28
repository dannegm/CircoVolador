package com.amb.circovolador.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Config;
import com.amb.circovolador.fragments.Evento;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import me.relex.circleindicator.CircleIndicator;

public class Eventos extends Activity {
    Context ctx;
    Activity atx;
    Config config;

    ViewPager activityPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        ctx = this;
        atx = this;
        config = new Config(ctx);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#0b3646"));

        Typeface sixcaps = Typeface.createFromAsset(getAssets(), "fonts/sixcaps.ttf");

        TextView TitleBar = (TextView) findViewById(R.id.TitleBar);
        TitleBar.setTypeface(sixcaps);

        activityPager = (ViewPager) findViewById(R.id.eventsPager);
        activityPager.setAdapter(new EventoPagerApadter(ctx, getSupportFragmentManager()));

        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
        circleIndicator.setViewPager(activityPager);
    }

    class EventoPagerApadter extends FragmentPagerAdapter {
        Context superCtx;

        public EventoPagerApadter (Context _ctx, FragmentManager fm) {
            super(fm);
            superCtx = _ctx;
        }
        public Fragment getItem (int position) {
            Evento event = new Evento (superCtx, atx, position);
            return event;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
