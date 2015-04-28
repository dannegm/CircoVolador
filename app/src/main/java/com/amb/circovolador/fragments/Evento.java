package com.amb.circovolador.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Config;

@SuppressLint("ValidFragment")
public class Evento extends Fragment {
    Context ctx;
    Activity atx;
    Config config;

    public Evento (Context _ctx, Activity _atx, int pos) {
        ctx = _ctx;
        atx = _atx;
        config = new Config(ctx);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout;
        layout = inflater.inflate(R.layout.fragment_evento, container, false);
        return layout;
    }
}
