package Good_Code;

import Good_Code.Interfaces.PlaylistIterator;


public class IteratorPatternGoodMain {
    public static void main(String[] args) {
        // Create a Playlist
        Playlist playlist = new Playlist();
        playlist.addSong("Dil Deewana");
        playlist.addSong("Mere khawabo mein fav");
        playlist.addSong("Dil Dharkaye");
        playlist.addSong("Sajni fav");
        playlist.addSong("Sajna fav");

        //Simple Playlist Iterator
        System.out.println("Simple Playlist: ");
        PlaylistIterator simpleIterator = playlist.iterator("Simple");
        while (simpleIterator.hasNext()) {
            System.out.println("Playing: " + simpleIterator.next());
        }

        //Shuffled Playlist Iterator
        System.out.println("Shuffled Playlist: ");
        PlaylistIterator shuffledIterator = playlist.iterator("Shuffled");
        while (shuffledIterator.hasNext()) {
            System.out.println("Playing: " + shuffledIterator.next());
        }

        //Favourite Playlist Iterator
        System.out.println("Favourite Playlist: ");
        PlaylistIterator favouriteIterator = playlist.iterator("Favourites");
        while (favouriteIterator.hasNext()) {
            System.out.println("Playing: " + favouriteIterator.next());
        }
    }
}
