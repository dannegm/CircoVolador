package com.amb.circovolador.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amb.circovolador.R;
import com.amb.circovolador.fragments.Evento;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;


public class MainActivity extends Activity {
    private Class actx;
    private Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        actx = Eventos.class;
        start();

        Typeface varelaround = Typeface.createFromAsset(ctx.getAssets(), "fonts/varelaround_regular.ttf");
        TextView cvtxt = (TextView) findViewById(R.id.cvtxt);
        cvtxt.setTypeface(varelaround);

    }
    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        start();
    }

    private void start () {
        Intent intent = new Intent(this, actx);
        startActivity(intent);
    }
}
