package com.amb.circovolador.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Menu;
import com.amb.circovolador.fragments.Evento;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.h6ah4i.android.materialshadowninepatch.MaterialShadowContainerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.skyfishjy.library.RippleBackground;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class Eventos extends FragmentActivity {
    Context ctx;
    Activity atx;
    Menu menu;

    ViewPager eventsPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        ctx = this;
        atx = this;

        final FragmentManager sfm = getSupportFragmentManager();

        Typeface sixcaps = Typeface.createFromAsset(getAssets(), "fonts/sixcaps.ttf");

        TextView TitleBar = (TextView) findViewById(R.id.TitleBar);
        TitleBar.setTypeface(sixcaps);


        final View textEmpty = findViewById(R.id.textEmpty);
        textEmpty.setVisibility(View.INVISIBLE);

        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.wireSound);
        rippleBackground.startRippleAnimation();

        String hostname = getResources().getString(R.string.hostname);
        final AsyncHttpClient client = new AsyncHttpClient();
        client.get(hostname + "/eventos.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                rippleBackground.stopRippleAnimation();
                if (response.length() != 0) {
                    eventsPager = (ViewPager) findViewById(R.id.eventsPager);
                    eventsPager.setAdapter(new EventoPagerApadter(ctx, sfm, response));

                    ParallaxPagerTransformer parallax = new ParallaxPagerTransformer(R.id.imageEvent);
                    parallax.setSpeed(0.5f);
                    eventsPager.setPageTransformer(false, parallax);

                    CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
                    circleIndicator.setViewPager(eventsPager);
                } else {
                    textEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                super.onFailure(statusCode, headers, responseString, e);
                String msg = "[" + statusCode + "|c/eventos] " + e.getMessage();
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();

                rippleBackground.stopRippleAnimation();
                textEmpty.setVisibility(View.VISIBLE);
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                client.cancelAllRequests(true);

                rippleBackground.stopRippleAnimation();
                textEmpty.setVisibility(View.VISIBLE);
            }
        }, 60 * 1000);

        View touchListener = findViewById(R.id.touchListener);
        menu = new Menu(this, this, touchListener);
        menu.Navigation();

        Typeface varelaround = Typeface.createFromAsset(ctx.getAssets(), "fonts/varelaround_regular.ttf");
        final MaterialShadowContainerView popupEventos = (MaterialShadowContainerView) findViewById(R.id.popupEventos);
        for( int i = 0; i < popupEventos.getChildCount(); i++ ) {
            if (popupEventos.getChildAt(i) instanceof TextView) {
                TextView textPop = (TextView) popupEventos.getChildAt(i);
                textPop.setTypeface(varelaround);
            }
        }

        TextView textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setTypeface(sixcaps);

        View popEvento = findViewById(R.id.popEvento);
        popEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.BounceInUp)
                        .duration(300)
                        .playOn(popupEventos);
            }
        });
        popupEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutDown)
                        .duration(200)
                        .playOn(popupEventos);
            }
        });
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
            Evento event = new Evento (superCtx, position, data);
            return event;
        }

        @Override
        public int getCount() {
            return data.length();
        }
    }
}
