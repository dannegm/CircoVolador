package com.amb.circovolador.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Menu;
import com.amb.circovolador.fragments.Proyecto;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.skyfishjy.library.RippleBackground;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Proyectos extends FragmentActivity {
    Context ctx;
    Activity atx;
    Menu menu;

    ViewPager proyectPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);

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

        String hostname = getResources().getString(R.string.fbalbums);
        final AsyncHttpClient client = new AsyncHttpClient();
        client.get(hostname, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                rippleBackground.stopRippleAnimation();

                try {
                    JSONArray resp = response.getJSONObject("albums").getJSONArray("data");
                    JSONArray tempData = new JSONArray();

                    for (int x = 0; x < resp.length(); x++) {
                        boolean isDescription = resp.getJSONObject(x).has("description");
                        if (isDescription) {
                            tempData.put(resp.getJSONObject(x));
                        }
                    }

                    if (tempData.length() != 0){
                        proyectPager = (ViewPager) findViewById(R.id.proyectsPager);
                        proyectPager.setAdapter(new ProyectoPagerApadter(ctx, sfm, tempData));

                        ParallaxPagerTransformer parallax = new ParallaxPagerTransformer(R.id.proyectCover);
                        parallax.setSpeed(0.5f);
                        proyectPager.setPageTransformer(false, parallax);
                    } else {
                        textEmpty.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    textEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                super.onFailure(statusCode, headers, responseString, e);
                String msg = "[" + statusCode + "|f/albums] " + e.getMessage();
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
    }

    @Override
    public void onBackPressed() {
        if (menu.isOpen()) {
            menu.close();
        } else {
            super.onBackPressed();
            atx.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
        }
    }

    class ProyectoPagerApadter extends FragmentPagerAdapter {
        Context superCtx;
        JSONArray data;

        public ProyectoPagerApadter (Context _ctx, FragmentManager fm, JSONArray _data) {
            super(fm);
            superCtx = _ctx;
            this.data = _data;

            Log.i("[JSON]", _data.toString());
        }
        public Fragment getItem (int position) {
            Proyecto proyect = new Proyecto (superCtx, position, data);
            return proyect;
        }

        @Override
        public int getCount() {
            return data.length();
        }
    }
}
