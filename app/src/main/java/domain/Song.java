package domain;

public class Song {
    private String[] songs;
    private String[] songs_mp3;
    public Song(){
        songs = new String[]{"G.E.M.邓紫棋 - Walk On Water","G.E.M.邓紫棋 - 倒数", "G.E.M.邓紫棋 - 新的心跳","G.E.M.邓紫棋 - 桃花诺","接个吻，开一枪,沈以诚,薛明媛 - 失眠飞行"};
        songs_mp3 = new String[]{"G.E.M.邓紫棋 - Walk On Water.mp3","G.E.M.邓紫棋 - 倒数.mp3","G.E.M.邓紫棋 - 新的心跳.mp3","G.E.M.邓紫棋 - 桃花诺.mp3","接个吻，开一枪,沈以诚,薛明媛 - 失眠飞行.mp3"};
    }

    public String[] getSongs() {
        return songs;
    }

    public void setSongs(String[] songs) {
        this.songs = songs;
    }

    public String[] addSong(String song){
        String[] newSongs = new String[songs.length + 1];
        for(int i = 0; i < songs.length; i++)
            newSongs[i] = songs[i];
        newSongs[songs.length] = song;
        return newSongs;
    }

    public String[] delSong(int id){
        String[] newSongs = new String[songs.length - 1];
        for(int i = 0; i < id; i++)
            newSongs[i] = songs[i];
        for(int i = id + 1; i < songs.length; i++)
            newSongs[i - 1] = songs[i];
        return newSongs;
    }
}
