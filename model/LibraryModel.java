package model;

import java.util.*;
import java.security.SecureRandom;
import java.util.Base64;
import static model.MainModel.hashPassword;


import com.sun.jdi.Value;

/*
 * Class: LibraryModel
 * Purpose: The main database for the user's experience.
 * Stores the users Songs, albums, and playlists.
 */
public class LibraryModel {

	private Playlist mostPlayed; // TODO: move this into playlist data structure
	private Playlist recentPlayed; // TODO: move this into playlist data structure
	private HashMap<String, ArrayList<Album>> albumList;
	private HashMap<String, Playlist> playlists;
	private MusicStore store;
	private ArrayList<Song> playedSongs; 
	
	private HashMap<String, ArrayList<Song>> songNames;

	private final String PASSWORD;
	private String finalSalt;

	public LibraryModel(String password) {
		
		albumList = new HashMap<String, ArrayList<Album>>();
		songNames = new HashMap<String, ArrayList<Song>>();
		playlists = new HashMap<String, Playlist>();
		store = new MusicStore();

		finalSalt = createSalt();

		PASSWORD = hashPassword(password, finalSalt);

		playedSongs = new ArrayList<Song>();
		mostPlayed = new Playlist("Recent");
		recentPlayed = new Playlist("mostListened");

	}

	public boolean playSong(String songTitle, String artist) {
		ArrayList<Song> foundSongs = songNames.get(songTitle);
		
		Song value = null;
		
		for (Song s : foundSongs) {
			if (s.getAuthor().toLowerCase().equals(artist.toLowerCase())) {
				value = s;
			}
		}
		
		if (value != null) {
			value.playSong();
			playedSongs.add(value);
			if (recentPlayed.getSize() >= 10) {
				recentPlayed.removeFirst();
			}
			recentPlayed.addSong(value);
			return true;
		}
		
		return false;
	}

	// sorting from most played to least played
	public void selectionSortSongs() {
		int size = playedSongs.size();

		for (int i = 0; i < size - 1; i++) {
			int value = i;
			for (int j = i + 1; j < size; j++) {
				if (playedSongs.get(j).getTimesPlayed() > playedSongs.get(value).getTimesPlayed()) {
					value = j;
				}
			}
			if (value != i) {
				Song temp = playedSongs.get(i);
				playedSongs.set(i, playedSongs.get(value));
				playedSongs.set(value, temp);
			}
		}
	}

	public Playlist getTopSongs() {
		if (playedSongs.size() <= 10) {
			for (Song s : playedSongs) {
				mostPlayed.addSong(s);
			}

		} else {
			selectionSortSongs();
			List<Song> topSongs = playedSongs.subList(0, 10);
			for (Song s : topSongs) {
				mostPlayed.addSong(s);
			}
		}
		return mostPlayed;
	}

	/*
	 * Method: createSalt() Purpose: create the salt to be used on this account's
	 * password
	 */
	private String createSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	/*
	 * Method: checkPassword() Purpose: Compare an inputed password to the correct
	 * password
	 */
	public boolean checkPassword(String password) {

		return hashPassword(password, finalSalt).equals(PASSWORD);

	}

	/*
	 * Method: searchSongTitle(title) Purpose: search for a song by title and return
	 * a list of found songs
	 */
	public ArrayList<Song> searchSongTitle(String title) {

		return songNames.get(title);
	}

	/*
	 * Method: searchSongArtist(artist) Purpose: search for a song by artist and
	 * return a list of found songs
	 */
	public ArrayList<Song> searchSongArtist(String artist) {

		ArrayList<Song> foundSongs = new ArrayList<Song>();
		for (ArrayList<Song> list : songNames.values()) {
			for (Song s : list) {
				if (s.getAuthor().equals(artist)) {
					foundSongs.add(s);
				}
			}
		}

		return foundSongs;
	}

	/*
	 * Method: searchAlbumTitle(albumName) Purpose: search for an album by name and
	 * print its contents
	 */
	public ArrayList<Album> searchAlbumTitle(String albumName) {

		return albumList.get(albumName);
	}

	/*
	 * Method: searchAlbumArtist(artistName) Purpose: search for an album by artist
	 * and print its contents
	 */
	public ArrayList<Album> searchAlbumArtist(String artistName) {

		ArrayList<Album> foundAlbums = new ArrayList<Album>();
		for (ArrayList<Album> list : albumList.values()) {
			for (Album a : list) {
				if (a.getAuthorName().toLowerCase().equals(artistName.toLowerCase())) {
					foundAlbums.add(a);
				}
			}
		}

		return foundAlbums;
	}

	/*
	 * Method: searchPlaylist Purpose: search for a playlist with name and return a
	 * lists of playlists with playlistName
	 */
	public ArrayList<Playlist> searchPlaylist(String playlistName) {
		
		ArrayList<Playlist> newList = new ArrayList<Playlist>();
		
		newList.add(playlists.get(playlistName));

		return newList;
	}

	/*
	 * Method: hasAlbum(title) Purpose: check if an album with name title is found
	 * in the albumList
	 */
	private boolean hasAlbum(String title) {

		return albumList.containsKey(title);

	}

	/*
	 * Method: hasSong(title) Purpose: check if an album with name title is found in
	 * the albumList
	 */
	private boolean hasSong(Album a, String title) {

		for (Song s : a.getSongs()) {
			if (s.getName().toLowerCase().equals(title.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	
	private Album existingAlbumMatchesAuthor(Song foundSong) {
		ArrayList<Album> existingAlbums = albumList.get(foundSong.getAlbum());
		
		if (existingAlbums == null) {
			return null;
		}
		
		for (Album a : existingAlbums) {
			if (a.getAuthorName().equals(foundSong.getAuthor())) {
				return a;
			}
		}
		
		return null;
	}
	
	/*
	 * Method: addSong(title) Purpose: add a song to the user library into the
	 * appropriate album, make the album if there isn't one already
	 */
	public boolean addSong(String title) {

		ArrayList<Song> foundSongs = store.searchSong(title);

		if (foundSongs.size() == 0) {
			return false;
		}

		Song foundSong = foundSongs.get(0);
		Album albumToAddTo = existingAlbumMatchesAuthor(foundSong);

		// If the album isn't in our library, we make it
		if (!albumList.containsKey(foundSong.getAlbum())) {
			for (Album a : store.getAlbums()) {
				if (a.getAlbumName().equals(foundSong.getAlbum())) {
					Album newAlbum = new Album(a.getAuthorName(), a.getAlbumName(), a.getYear(), a.getGenre());
					ArrayList<Album> newList = new ArrayList<Album>();
					newList.add(newAlbum);
					albumList.put(foundSong.getAlbum(), newList);
					albumToAddTo = newAlbum;
				}
			}
		} else if (albumToAddTo == null){
			for (Album a : store.getAlbums()) {
				if (a.getAlbumName().equals(foundSong.getAlbum())) {
					Album newAlbum = new Album(a.getAuthorName(), a.getAlbumName(), a.getYear(), a.getGenre());
					albumList.get(foundSong.getAlbum()).add(newAlbum);
					albumToAddTo = newAlbum;
				}
			}
		}

		albumToAddTo.addSong(foundSong);
		
		/*
		 * Add to the song collections
		 */
		if (songNames.containsKey(foundSong.getName())) {
			
			// if the song in the hashmap list isn't the same song as the found song
			for (Song s : songNames.get(foundSong.getName())) {
				if (s.getAuthor().equals(foundSong.getAuthor())) {
					return false;
				}
			}
			songNames.get(foundSong.getName()).add(foundSong);
			return true;
		} else {
			ArrayList<Song> newList = new ArrayList<Song>();
			newList.add(foundSong);
			songNames.put(foundSong.getName(), newList);
			return true;
		}
	}

	/*
	 * Method: addAlbum(title) Purpose: add an entire album to the user's library,
	 * if its in the store and not already in the user's library
	 */
	public boolean addAlbum(String title) {

		ArrayList<Album> foundAlbums = store.searchAlbum(title);

		if (foundAlbums.size() == 0) {
			return false;
		}

		Album foundAlbum = foundAlbums.get(0);

		if (albumList.containsKey(title)) {
			for (Album a : albumList.get(title)) {
				if (a.getAuthorName().equals(foundAlbum.getAuthorName())) {
					for (Song s : foundAlbum.getSongs()) {
						if (!hasSong(a, s.getName())) {
							a.addSong(s);
						}
					}
					return true;
				}
			}
		}


		Album newAlbum = new Album(foundAlbum.getAuthorName(), foundAlbum.getAlbumName(), foundAlbum.getYear(),
				foundAlbum.getGenre());
		ArrayList<Album> newList = new ArrayList<Album>();
		newList.add(newAlbum);
		albumList.put(newAlbum.getAuthorName(), newList);

		for (Song s : foundAlbum.getSongs()) {
			newAlbum.addSong(s);
		}

		return true;
	}

	/*
	 * Method: getSongTitles() Purpose: print to the user a list of song titles
	 */
	public ArrayList<String> getSongTitles() {

		ArrayList<String> songs = new ArrayList<String>();

		for (ArrayList<Song> list : songNames.values()) {
			for (Song s : list) {
				songs.add(s.getName());
			}
		}

		return songs;
	}

	/*
	 * Method: getArtists() Purpose: print to the user a list of artists
	 */
	public ArrayList<String> getArtists() {
		ArrayList<String> printedArtists = new ArrayList<String>();

		for (ArrayList<Album> list : albumList.values()) {
			for (Album a : list) {
				if (!printedArtists.contains(a.getAuthorName())) {
					printedArtists.add(a.getAuthorName());
				}
			}
		}

		return printedArtists;
	}

	/*
	 * Method: getAlbums() Purpose: print to the user a list of albums
	 */
	public ArrayList<Album> getAlbums() {

		ArrayList<Album> newList = new ArrayList<Album>();

		for (ArrayList<Album> list : albumList.values()) {
			for (Album a : list) {
				newList.add(new Album(a.getAuthorName(), a.getAlbumName(), a.getYear(), a.getGenre()));
				for (Song s : a.getSongs()) {
					a.addSong(s);
				}
			}
		}

		return newList;
	}

	/*
	 * Method: getPlaylists() Purpose: print to the user their list of playlists
	 */
	public ArrayList<String> getPlaylists() {

		ArrayList<String> playlistNames = new ArrayList<String>();

		for (Playlist p : playlists.values()) {
			playlistNames.add(p.getName());
		}

		return playlistNames;
	}

	/*
	 * Method: getSongsInPlaylist(title) Purpose: print out the songs in a playlist
	 */
	public ArrayList<String> getSongsInPlaylist(String title) {

		ArrayList<String> songs = new ArrayList<String>();

		Playlist p = playlists.get(title);
		if (p != null) {
			for (Song s : p.getSongs()) {
				songs.add(s.getName());
			}
		}

		return songs;
	}

	/*
	 * Method: getFavoriteSongs() Purpose: print out the users favorite songs
	 */
	public ArrayList<String> getFavoriteSongs() {

		ArrayList<String> favSongs = new ArrayList<String>();

		for (ArrayList<Song> list : songNames.values()) {
			for (Song s : list) {
				if (s.isFavorite()) {
					favSongs.add(s.getName());
				}
			}
		}

		return favSongs;
	}

	/*
	 * Method: createPlaylist(name) Purpose: creates a new playlist with name
	 */
	public boolean createPlaylist(String name) {

		if (playlists.containsKey(name)) {
			return false;
		}

		playlists.put(name, new Playlist(name));
		return true;
	}

	/*
	 * Method: removePlaylist(name) Purpose: removes a playlist with name
	 */
	public boolean removePlaylist(String name) {
		return playlists.remove(name) != null;
	}

	/*
	 * Method: addToPlaylist Purpose: add a song by title and artist into a playlist
	 */
	public boolean addToPlaylist(String playlist, String name, String artist) {
		ArrayList<Song> foundSongs = this.searchSongTitle(name);

		Playlist foundList = playlists.get(playlist);

		if (foundList != null) {
			if (foundSongs.size() > 0) {
				for (Song s : foundSongs) {
					if (s.getAuthor().toLowerCase().equals(artist.toLowerCase())) {
						foundList.addSong(s);
						return true;
					}
				}
			}
		}

		return false;
	}

	/*
	 * Method: removeFromPlaylist Purpose: remove a song by title from a playlist
	 */
	public boolean removeFromPlaylist(String playlist, String name) {

		Playlist foundList = playlists.get(playlist);
		
		if (foundList != null) {
			return foundList.removeSong(name);
		}

		return false;
	}

	/*
	 * Method: rateSong Purpose: search for a song by title and artist and assign it
	 * a rating
	 */
	public boolean rateSong(String songName, String artist, int rating) {

		if (songNames.containsKey(songName)) {
			for (Song s : songNames.get(songName)) {
				if (s.getAuthor().equals(artist)) {
					s.setRating(rating);
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Method: setFavorite() Purpose: search for a song by title and artist and set
	 * to favorite or unfavorite
	 */
	public boolean setFavorite(String songName, String artist, boolean favorite) {

		if (songNames.containsKey(songName)) {
			for (Song s : songNames.get(songName)) {
				if (s.getAuthor().equals(artist)) {
					s.setFavorite(favorite);
					return true;
				}
			}
		}

		return false;
	}

	public MusicStore getStore() {
		return this.store;
	}
}
