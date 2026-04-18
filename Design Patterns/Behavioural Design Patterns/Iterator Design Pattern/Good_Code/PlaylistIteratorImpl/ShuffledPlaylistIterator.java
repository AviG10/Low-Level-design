package Good_Code.PlaylistIteratorImpl;

import Good_Code.Interfaces.PlaylistIterator;
import Good_Code.Playlist;

import java.util.ArrayList;
import java.util.Collections;

public class ShuffledPlaylistIterator implements PlaylistIterator {
    private int index;
    private final ArrayList<String> shuffledPlaylist;

    public ShuffledPlaylistIterator(Playlist playlist) {
        this.index = 0;
        this.shuffledPlaylist = new ArrayList<>(playlist.getSongs());
        Collections.shuffle(shuffledPlaylist);
    }

    @Override
    public boolean hasNext() {
        return index < shuffledPlaylist.size();
    }

    @Override
    public String next() {
        return shuffledPlaylist.get(index++);
    }
}
