package Good_Code.PlaylistIteratorImpl;

import Good_Code.Interfaces.PlaylistIterator;
import Good_Code.Playlist;

public class SimplePlaylistIterator implements PlaylistIterator {
    private final Playlist playlist;
    private int index;

    public SimplePlaylistIterator(Playlist playlist) {
        this.playlist = playlist;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < playlist.getSongs().size();
    }

    @Override
    public String next() {
        return playlist.getSongs().get(index++);
    }
}
