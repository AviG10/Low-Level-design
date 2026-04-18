package Good_Code;

import Good_Code.Interfaces.PlaylistIterator;
import Good_Code.PlaylistIteratorImpl.FavouritePlaylistIterator;
import Good_Code.PlaylistIteratorImpl.ShuffledPlaylistIterator;
import Good_Code.PlaylistIteratorImpl.SimplePlaylistIterator;

import java.util.ArrayList;

public class Playlist {
    private final ArrayList<String>  songs;

    public Playlist() {
        this.songs = new ArrayList<>();
    }

    public void addSong(String song) {
        this.songs.add(song);
    }

    public ArrayList<String> getSongs() {
        return songs;
    }

    public PlaylistIterator iterator(String type) {
        return switch (type) {
            case "Simple" -> new SimplePlaylistIterator(this);
            case "Shuffled" -> new ShuffledPlaylistIterator(this);
            case "Favourites" -> new FavouritePlaylistIterator(this);
            default -> throw new IllegalArgumentException("Invalid playlist type : "+ type);
        };
    }
}

