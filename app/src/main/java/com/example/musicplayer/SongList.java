package com.example.musicplayer;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

public class SongList extends MainActivity {

    private TextView song_item;
    private RelativeLayout up;
    private TextView song_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_song_list);
        song_item = (TextView)findViewById(R.id.song_item);
        song_item.bringToFront();
        up = findViewById(R.id.up);
        up.bringToFront();
        up.getBackground().setAlpha(100);
        song_name = findViewById(R.id.song_name);
        File file = new File("E:\\AndroidStudioProjects\\music_forMusicPlayer");
        String []filename = file.list();
        for(int a = 0; a < 3; a++) {
            System.out.println(filename[a]);
        }
    }
}
