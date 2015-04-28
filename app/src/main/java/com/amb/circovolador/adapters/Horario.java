package com.amb.circovolador.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amb.circovolador.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Horario extends BaseAdapter {
    private Context mContext;
    private final JSONArray horarios;

    public Horario (Context c, JSONArray horarios) {
        mContext = c;
        this.horarios = horarios;
    }

    @Override
    public int getCount() {
        return horarios.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list = null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            try {
                list = new View(mContext);
                list = inflater.inflate(R.layout.adapter_horario, null);

                JSONObject horario = horarios.getJSONObject(position);
                Typeface varelaround = Typeface.createFromAsset(mContext.getAssets(), "fonts/varelaround_regular.ttf");

                TextView group = (TextView) list.findViewById(R.id.textGroup);
                group.setText( "Grupo " + String.valueOf(position + 1) );
                group.setTypeface(varelaround);

                TextView hora = (TextView) list.findViewById(R.id.textHorario);
                hora.setText( horario.getString("horario") );
                hora.setTypeface(varelaround);

            } catch (JSONException e) {}

        } else {
            list = convertView;
        }
        return list;
    }
}
