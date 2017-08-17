package com.metalsoft.droidpong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anthony Ratliff on 8/3/2017.
 */

public class HighScoreAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Player> players;

    // Class Constructor accepts a context and an array list.
    public HighScoreAdapter(Context c, ArrayList<Player> list) {
        if (c != null && list != null){
            this.context = c;
            this.players = list;
        }

    }

    @Override
    public int getCount() {
        return this.players.size();
    }

    @Override
    public Object getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_score_list, parent, false);
        }

        TextView posTxt = (TextView) convertView.findViewById(R.id.text_position);
        TextView nameTxt = (TextView) convertView.findViewById(R.id.text_name);
        TextView hitsTxt = (TextView) convertView.findViewById(R.id.text_hits);
        TextView ratioTxt = (TextView) convertView.findViewById(R.id.text_ratio);

        posTxt.setText(String.valueOf(position + 1));
        String name = players.get(position).getPlayerName();
        if (name.length() > 16){
            nameTxt.setText(name.substring(0, 12) + "...");
        } else {
            nameTxt.setText(name);
        }
        hitsTxt.setText(String.valueOf(players.get(position).getBallHits()));
        ratioTxt.setText(String.valueOf(players.get(position).getGameRatio() + "%"));

        return convertView;
    }
}
