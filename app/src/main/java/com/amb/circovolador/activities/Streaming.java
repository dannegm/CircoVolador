package com.amb.circovolador.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amb.circovolador.R;
import com.amb.circovolador.Utils.Config;
import com.amb.circovolador.Utils.Menu;
import com.amb.circovolador.customViews.CustomVideoView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.skyfishjy.library.RippleBackground;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class Streaming extends Activity {
    Context ctx;
    Activity atx;
    Config config;
    Menu menu;

    AsyncHttpClient client;
    String hostname;

    TextView textSong;
    RippleBackground rippleBackground;
    ImageView playPause;
    CustomVideoView player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);

        ctx = this;
        atx = this;
        config = new Config(ctx);
        client = new AsyncHttpClient();

        hostname = getResources().getString(R.string.hostname);

        Typeface sixcaps = Typeface.createFromAsset(getAssets(), "fonts/sixcaps.ttf");
        Typeface varelaround = Typeface.createFromAsset(ctx.getAssets(), "fonts/varelaround_regular.ttf");

        TextView TitleBar = (TextView) findViewById(R.id.TitleBar);
        TitleBar.setTypeface(sixcaps);

        TextView textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setTypeface(sixcaps);

        TextView textNow = (TextView) findViewById(R.id.textNow);
        textNow.setTypeface(varelaround);

        textSong = (TextView) findViewById(R.id.textSong);
        textSong.setTypeface(varelaround);

        // Player aquí

        client.get(hostname + "/stream.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject radio = response.getJSONObject("radio");
                    Uri stream = Uri.parse(radio.getString("rtsp"));
                    makeStream(stream);
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                super.onFailure(statusCode, headers, responseString, e);
                String msg = "[" + statusCode + "|c/stream] " + e.getMessage();
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
            }
        });

        menu = new Menu(this, this);
        menu.Navigation();
    }

    public void makeStream (Uri stream) {
        rippleBackground = (RippleBackground) findViewById(R.id.wireSound);
        playPause = (ImageView) findViewById(R.id.btnPlayPause);
        player = (CustomVideoView) findViewById(R.id.videoView);
        player.setVideoURI(stream);

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.isPlaying()) {
                    player.start();
                } else {
                    player.pause();
                }
            }
        });
        player.setPlayPauseListener(new CustomVideoView.PlayPauseListener() {
            @Override
            public void onPlay() {
                rippleBackground.startRippleAnimation();
                playPause.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));
            }

            @Override
            public void onPause() {
                rippleBackground.stopRippleAnimation();
                playPause.setImageDrawable(getResources().getDrawable(R.drawable.btn_play));
            }
        });
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(ctx, getResources().getString(R.string.error_load_stream), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Change Title

        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private long time = 0;

            @Override
            public void run() {
                if (!player.isPlaying()) {
                    loadSong();
                }
                time += 1000;
                h.postDelayed(this, 15000);
            }
        }, 1000);
    }

    public void loadSong () {
        client.get(hostname + "/song.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    textSong.setText(response.getString("song"));
                } catch (JSONException e) { }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                super.onFailure(statusCode, headers, responseString, e);
                textSong.setText(getResources().getString(R.string.cvr_lower));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (menu.isOpen()) {
            menu.close();
        }
    }
}
