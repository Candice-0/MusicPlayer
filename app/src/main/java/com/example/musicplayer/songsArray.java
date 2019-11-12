package com.example.musicplayer;

public class songsArray {
    //歌名数组
    public static String[] songs = new String[]{"G.E.M.邓紫棋-Walk On Water","G.E.M.邓紫棋-倒数", "G.E.M.邓紫棋-新的心跳","G.E.M.邓紫棋-桃花诺","接个吻，开一枪，沈以诚，薛明媛-失眠飞行"};
    //MP3数组
    public String[] song_mp3 = new String[]{"G.E.M.邓紫棋 - Walk On Water.mp3","G.E.M.邓紫棋-倒数.mp3","G.E.M.邓紫棋-新的心跳.mp3","G.E.M.邓紫棋-桃花诺.mp3","接个吻，开一枪，沈以诚，薛明媛-失眠飞行.mp3"};

    public String chooseSong(int id){
        return songs[id];
    }
}
