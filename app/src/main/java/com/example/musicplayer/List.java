package com.example.musicplayer;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class List extends AppCompatActivity {
    private static final String TAG="myTag";

    public MediaPlayer mediaPlayer;

    /*播放控制*/
    private ImageView b_start;//播放按钮
    private ImageView b_last;//上一首
    private ImageView b_next;//下一首

    /*进度条控制*/
    static SeekBar seekBar;//进度条
    private TextView time_left;//进度条左边的当前歌曲播放时间
    private TextView time_right;//进度条右边的歌曲总时长

    /*碟片控制*/
    private ImageView disk;//碟片
    private ImageView needle;//碟片上方播放针
    private ObjectAnimator objectAnimator;//控制碟片旋转函数
    private ObjectAnimator objectAnimator_needle;

    //private String[] a;//歌曲名单 用数组的方式存储 路径名
    private int b = 0;//用b来控制数组a
    private TextView songs_name;

    private ImageView back;//返回按钮
    private ImageView add_item;//添加到我的歌单按钮

    private boolean flag = false;
    private int music_id;

    private TextView song_name;
    private int song_id;
    public String[] SongName;

    //歌名数组
    public String[] songs = new String[]{"G.E.M.邓紫棋 - Walk On Water","G.E.M.邓紫棋 - 倒数", "G.E.M.邓紫棋 - 新的心跳","G.E.M.邓紫棋 - 桃花诺","接个吻，开一枪,沈以诚,薛明媛 - 失眠飞行"};
    //MP3数组
    public String[] mp3 = new String[]{"G.E.M.邓紫棋 - Walk On Water.mp3","G.E.M.邓紫棋 - 倒数.mp3","G.E.M.邓紫棋 - 新的心跳.mp3","G.E.M.邓紫棋 - 桃花诺.mp3","接个吻，开一枪,沈以诚,薛明媛 - 失眠飞行.mp3"};
    String[] a = new String[mp3.length];

    public ArrayList<String> M;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer=new MediaPlayer();

        b_last=findViewById(R.id.b_last);
        b_next=findViewById(R.id.b_next);
        disk=findViewById(R.id.disk);
        needle=findViewById(R.id.needle);
        time_left=findViewById(R.id.time_left);
        back=findViewById(R.id.back);
        seekBar=findViewById(R.id.seek_bar);
        needle.bringToFront();


        //back：返回songs_items页面（intent跳转）
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(List.this,MainActivity.class);
                startActivity(intent);

            }
        });

        //获取点击的歌曲名
        final Intent intent = getIntent();
        final String id = intent.getStringExtra("song_id");
        song_id = Integer.parseInt(id);
        Log.d("点击的歌曲id",id);
        Log.d("点击的歌曲名",songs[song_id]);
        song_name = (TextView)findViewById(R.id.song_name);
        song_name.setText("      " + songs[song_id]);

        for(int i = 0; i < mp3.length; i++){
            a[i] = mp3[i];
        }
        b_start = (ImageView) findViewById(R.id.b_start);
        b_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == false) {
                    //点击start按钮转成pause按钮
                    b_start.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_pause));
                    //handler下面
                    handler.sendEmptyMessage(1);
                    musicPlay(a, song_id);
                    diskMove();
                    needleMove();
                    mediaPlayer.start();
                    Log.d("MainActivity", String.valueOf(flag));
                    flag = true;
                }else{
                    mediaPlayer.pause();
                    diskStop();
                    b_start.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
                    flag = false;
                }
            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                song_id++;
                if(song_id > mp3.length - 1){
                    song_id--;
                    Toast.makeText(List.this,"已经到最后一首歌了😯",Toast.LENGTH_LONG).show();
                }else {
                    musicPlay(a, song_id);
                }
            }
        });

        b_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                song_id--;
                if(song_id < 0){
                    song_id++;
                    Toast.makeText(List.this,"没有上一首歌了大兄弟",Toast.LENGTH_LONG).show();
                }else {
                    musicPlay(a, song_id);
                }
            }
        });

        //时间轴滚动
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress=seekBar.getProgress();
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void musicPlay(String a[],int b){
        song_name = findViewById(R.id.song_name);
        mediaPlayer.reset();
        AssetManager assetManager = getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(a[b]);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            getMusicTime_Total();

            mediaPlayer.start();
            song_name.setText(a[b]);

        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new SeekBarThread()).start();

    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            seekBar.setMax(mediaPlayer.getDuration());
            time_left.setText(getMusicTime_Current());

        }
    };
    class SeekBarThread implements Runnable{

        @Override
        public void run() {
            while (mediaPlayer!=null){
                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private void diskMove(){
        objectAnimator= ObjectAnimator.ofFloat(disk,"rotation",0f,360.f);
        objectAnimator.setDuration(10000);
        objectAnimator.setInterpolator(new LinearInterpolator());//
        objectAnimator.setRepeatCount(-1);//设置动画重复次数（-1代表一直转）
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式
        objectAnimator.start();
    }
    private void diskStop(){
        objectAnimator.end();
    }
    private void needleMove(){
        objectAnimator_needle=ObjectAnimator.ofFloat(needle,"translationY",0f,60f,0);
        objectAnimator_needle.setDuration(3000);
        objectAnimator_needle.start();
    }
    private String getMusicTime_Current(){
        time_left = findViewById(R.id.time_left);
        //当前歌曲的播放时间
        int time_now =mediaPlayer.getCurrentPosition()/1000;
        String time_now_show=time_now/60+":"+time_now%60;
        return time_now_show;
    }
    private void getMusicTime_Total(){

        time_right = findViewById(R.id.time_right);
        //歌曲总的播放时间
        int time_total=mediaPlayer.getDuration()/1000;
        String time_total_show=time_total/60+":"+time_total%60;
        time_right.setText(time_total_show);
    }
}
