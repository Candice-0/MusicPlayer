package com.example.musicplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Song;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView song_item;;
    private RelativeLayout r1;
    private TextView filename;
    private ArrayList<HashMap<String, Object>> list;
    private TextView song_name;
    private AssetManager assetManager;
    private String[] listFileName;
    private ImageView random;
    private ArrayList<HashMap<String,Object>> listItem;
    private ImageView play_all;
    public  TextView write_name;
    private ImageView love;
    private SimpleAdapter simpleAdapter;
    private Button go_item;

    //歌曲
    Song songs = new Song();

    /*选择跳转页面按钮*/
    private Button myList;//左边代表进入我的歌单
    private Button play;//右边代表进入音乐播放界面

    private ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_show);


        //进入正在播放的页面
        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, List.class);
                startActivity(intent);
            }
        });

        Click();
    }

    public void Click(){
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, songs.getSongs());
        listView = (ListView)findViewById(R.id.myList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, List.class);
                String i = "" + position;
                intent.putExtra("song_id", i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder delete = new AlertDialog.Builder(MainActivity.this);
                final LayoutInflater inflater = getLayoutInflater();
                final View v1 = inflater.inflate(R.layout.delete_dialog, null);
                delete.setView(v1).setTitle("").setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                delete.show();
                return false;
            }
        });
    }
}
