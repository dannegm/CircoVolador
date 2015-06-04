package com.amb.circovolador.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amb.circovolador.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.skyfishjy.library.RippleBackground;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;

public class StreamingService extends Service {
    Context ctx;
    AsyncHttpClient client;
    String hostname;

    Uri stream;
    MediaPlayer player;

    public StreamingService() {
        client = new AsyncHttpClient();
        hostname = getResources().getString(R.string.hostname);
        player = new MediaPlayer();

        client.get(hostname + "/stream.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject radio = response.getJSONObject("radio");
                    stream = Uri.parse(radio.getString("rtsp"));
                } catch (JSONException e) { }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                super.onFailure(statusCode, headers, responseString, e);
                String msg = "[" + statusCode + "|c/stream] " + e.getMessage();
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void startStreaming () {
        Play();
    }
    public void pauseStreaming () {

    }


    private void Play () {
        try {
            player.release();
            player.reset();
            player.setDataSource(ctx, stream);
            player.prepareAsync();

            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.start();
        } catch (IOException e) {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onCreate () {
        ctx = this;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        player.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
