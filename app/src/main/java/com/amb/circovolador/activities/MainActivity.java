package com.amb.circovolador.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.amb.circovolador.R;


public class MainActivity extends Activity {
    private Class actx;
    private Context ctx;
    private Activity atx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        atx = this;

        Typeface varelaround = Typeface.createFromAsset(ctx.getAssets(), "fonts/varelaround_regular.ttf");
        TextView cvtxt = (TextView) findViewById(R.id.cvtxt);
        cvtxt.setTypeface(varelaround);

        actx = Streaming.class;
        start();
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
        atx.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
    }
}
