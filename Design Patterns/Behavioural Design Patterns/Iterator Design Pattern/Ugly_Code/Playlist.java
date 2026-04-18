package Ugly_Code;

import java.util.ArrayList;
import java.util.Collections;

public class Playlist {
    private final ArrayList<String> songs;

    public Playlist(){
        this.songs = new  ArrayList<>();
    }

    public void addSong(String song){
        this.songs.add(song);
    }

    public void playPlaylist(boolean shuffle){
        if(shuffle){
            Collections.shuffle(this.songs);
            for (String song : songs) {
                System.out.println("Shuffled Playlist Songs : " + song);
            }
        }
        else{
            for (String song : songs) {
                System.out.println("Playlist Songs : " + song);
            }
        }

    }
}
