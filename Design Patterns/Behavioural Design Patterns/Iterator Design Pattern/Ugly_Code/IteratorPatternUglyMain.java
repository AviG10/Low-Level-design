package Ugly_Code;

public class IteratorPatternUglyMain {
    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        playlist.addSong("Dil Deewana");
        playlist.addSong("Mere khawabo mein");
        playlist.addSong("Dil Dharkaye");

        playlist.playPlaylist(false);
        playlist.playPlaylist(true);
    }
}
