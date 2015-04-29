package com.amb.circovolador.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.amb.circovolador.R;
import com.amb.circovolador.fragments.Evento;


public class MainActivity extends Activity {
    private Class actx;
    private Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        actx = Eventos.class;

        Intent intent = new Intent(this, actx);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, actx);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent = new Intent(this, actx);
        startActivity(intent);
    }
}
