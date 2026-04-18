package Good_Code.PlaylistIteratorImpl;

import Good_Code.Interfaces.PlaylistIterator;
import Good_Code.Playlist;

public class FavouritePlaylistIterator implements PlaylistIterator {
    private final Playlist playlist;
    private int index;

    public FavouritePlaylistIterator(Playlist playlist) {
        this.playlist = playlist;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        while (index < playlist.getSongs().size()) {
            if(playlist.getSongs().get(index).contains("fav")) {
                return true;
            }
            index++;
        }

        return false;
    }

    @Override
    public String next() {
        return playlist.getSongs().get(index++);
    }
}
