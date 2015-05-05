package com.amb.circovolador.Utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.amb.circovolador.R;
import com.amb.circovolador.activities.Eventos;
import com.amb.circovolador.activities.Proyectos;
import com.amb.circovolador.activities.Streaming;
import com.amb.circovolador.activities.Talleres;
import com.andexert.library.RippleView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import in.championswimmer.sfg.lib.SimpleFingerGestures;

/**
 * Created by ambmultimedia on 28/04/15.
 */
public class Menu {
    Context ctx;
    Activity atx;

    Boolean openMenu = false;

    RippleView toCartelera;
    RippleView toTalleres;
    RippleView toStreaming;
    RippleView toProyectos;

    LinearLayout menu;
    RelativeLayout overlive;

    public Menu (Context ctx, Activity atx, View touchListener) {
        this.ctx = ctx;
        this.atx = atx;

        init(touchListener);
    }

    public void init (View touchListener) {
        menu = (LinearLayout) atx.findViewById(R.id.menu);
        overlive = (RelativeLayout) atx.findViewById(R.id.overlive);
        overlive.setEnabled(false);
        overlive.setVisibility(View.INVISIBLE);

        toCartelera = (RippleView) atx.findViewById(R.id.toCartelera);
        toTalleres = (RippleView) atx.findViewById(R.id.toTalleres);
        toStreaming = (RippleView) atx.findViewById(R.id.toStreaming);
        toProyectos = (RippleView) atx.findViewById(R.id.toProyectos);

        RippleView toggleMenu = (RippleView) atx.findViewById(R.id.ContextMenu);
        toggleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!openMenu) {
                    open();
                } else {
                    close();
                }
            }
        });

        overlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openMenu) {
                    close();
                }
            }
        });

        SimpleFingerGestures touchEvents = new SimpleFingerGestures();
        touchEvents.setConsumeTouchEvents(true);
        touchEvents.setOnFingerGestureListener(new SimpleFingerGestures.OnFingerGestureListener() {
            @Override
            public boolean onSwipeUp(int fingers, long gestureDuration) {
                close();
                return false;
            }

            @Override
            public boolean onSwipeDown(int fingers, long gestureDuration) {
                open();
                return false;
            }

            @Override
            public boolean onSwipeLeft(int i, long l) { return false; }

            @Override
            public boolean onSwipeRight(int i, long l) { return false; }

            @Override
            public boolean onPinch(int i, long l) { return false; }

            @Override
            public boolean onUnpinch(int i, long l) { return false; }
        });
        touchListener.setOnTouchListener(touchEvents);
    }

    private void changeActivity (Class actv) {
        Class actualClass = ctx.getClass();
        close();
        if (actualClass != actv) {
            Intent intent = new Intent(ctx, actv);
            atx.startActivity(intent);
            atx.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
        }
    }

    public void Navigation () {
        toCartelera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Eventos.class);
            }
        });
        toTalleres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Talleres.class);
            }
        });
        toStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Streaming.class);
            }
        });
        toProyectos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Proyectos.class);
            }
        });
    }

    public void open () {
        ObjectAnimator anim_open = ObjectAnimator.ofFloat(menu, "translationY", 0);
        anim_open.setDuration(200);
        anim_open.start();
        openMenu = true;

        overlive.setEnabled(true);
        overlive.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(200)
                .playOn(overlive);

        YoYo.with(Techniques.SlideInDown)
                .duration(400)
                .playOn(toCartelera);
        YoYo.with(Techniques.SlideInDown)
                .duration(300)
                .playOn(toTalleres);
        YoYo.with(Techniques.SlideInDown)
                .duration(200)
                .playOn(toStreaming);
        YoYo.with(Techniques.SlideInDown)
                .duration(100)
                .playOn(toProyectos);
    }

    public void close () {
        ObjectAnimator anim_close = ObjectAnimator.ofFloat(menu, "translationY", -400);
        anim_close.setDuration(200);
        anim_close.start();
        openMenu = false;

        YoYo.with(Techniques.FadeOut)
                .duration(100)
                .playOn(overlive);

        overlive.setVisibility(View.INVISIBLE);
        overlive.setEnabled(false);
    }

    public boolean isOpen () {
        return openMenu;
    }
}
