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
	
	private Playlist mostPlayed;
	private Playlist recentPlayed;
	private ArrayList<Album> albumList;
	private ArrayList<Playlist> playlists;
	private MusicStore store;
	private ArrayList<Song> playedSongs;
	
	private final String PASSWORD;
	private String finalSalt;
	
	public LibraryModel(String password) {
		albumList = new ArrayList<Album>();
		playlists = new ArrayList<Playlist>();
		store = new MusicStore();
		
        finalSalt = createSalt();
        
        PASSWORD = hashPassword(password, finalSalt);
    
		playedSongs = new ArrayList<Song>();
		mostPlayed = new Playlist("Recent");
	  recentPlayed=new Playlist("mostListened");
		
	}
	
	public void playSong(String songTitle) {
	    Song value = null;
	    for (Album a: albumList) {
	        value = a.getSong(songTitle);
	        if (value!=null) {
	            break;
	        }
	    }
	    if (value != null) {
	        value.playSong();
	        playedSongs.add(value);
	        	if (recentPlayed.getSize() >= 10) {
	        		recentPlayed.removeFirst();
	        }
	        recentPlayed.addSong(value);
      }
	}
	
	//sorting from most played to least played
	 public void selectionSortSongs() {
	        int size = playedSongs.size();

	        for (int i = 0; i < size -1; i++) {
	            int value = i;
	            for (int j = i + 1; j < size; j++) {
	                if (playedSongs.get(j).getTimesPlayed()>playedSongs.get(value).getTimesPlayed()) {
	                    value = j;
	                }
	            }
	            if (value!=i) {
	                Song temp = playedSongs.get(i);
	                playedSongs.set(i, playedSongs.get(value));
	                playedSongs.set(value, temp);
	            }
	        }
  }

	public Playlist getTopSongs() {
		if(playedSongs.size()<=10) {
			for(Song s: playedSongs) {
				mostPlayed.addSong(s);
			}
			
		}
		else {
	      selectionSortSongs();
	      List<Song> topSongs = playedSongs.subList(0, 10);
	        for(Song s: topSongs) {
	        	mostPlayed.addSong(s);
	        }
		}
		return mostPlayed;
  }
	/*
	 * Method: createSalt()
	 * Purpose: create the salt to be used on this account's password
	 */
	private String createSalt() {
		SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
	}
	
	
	/*
	 * Method: checkPassword()
	 * Purpose: Compare an inputed password to the correct password
	 */
	public boolean checkPassword(String password) {
		
	  return hashPassword(password, finalSalt).equals(PASSWORD);
		
	}
	
	/*
	 * Method: searchSongTitle(title)
	 * Purpose: search for a song by title and return a list of found songs
	 */
	public ArrayList<Song> searchSongTitle(String title) {

		ArrayList<Song> foundSongs = new ArrayList<Song>();
		for (Album a : albumList) {
			Song songSearch = a.getSong(title);
			if (songSearch != null) {
				foundSongs.add(songSearch);
			}
		}
		
		return foundSongs;
	}
	
	/*
	 * Method: searchSongArtist(artist)
	 * Purpose: search for a song by artist and return a list of found songs
	 */
	public ArrayList<Song> searchSongArtist(String artist) {
		
		ArrayList<Song> foundSongs = new ArrayList<Song>();
		for (Album a : albumList) {
			if (a.getAuthorName().toLowerCase().equals(artist.toLowerCase())) {
				for (Song s : a.getSongs()) {
					foundSongs.add(s);
				}
			}
		}

		return foundSongs;
	}
	
	/*
	 * Method: searchAlbumTitle(albumName)
	 * Purpose: search for an album by name and print its contents
	 */
	public ArrayList<Album> searchAlbumTitle(String albumName) {
		
		ArrayList<Album> foundAlbums = new ArrayList<Album>();
		for (Album a : albumList) {
			if (a.getAlbumName().toLowerCase().equals(albumName.toLowerCase())) {
				foundAlbums.add(a);
			}
		}
		
		return foundAlbums;
	}
	
	/*
	 * Method: searchAlbumArtist(artistName)
	 * Purpose: search for an album by artist and print its contents
	 */
	public ArrayList<Album> searchAlbumArtist(String artistName) {
		
		ArrayList<Album> foundAlbums = new ArrayList<Album>();
		for (Album a : albumList) {
			if (a.getAuthorName().toLowerCase().equals(artistName.toLowerCase())) {
				foundAlbums.add(a);
			}
		}
		
		return foundAlbums;
	}
	
	
	/*
	 * Method: searchPlaylist
	 * Purpose: search for a playlist with name and return a lists of playlists with playlistName
	 */
	public ArrayList<Playlist> searchPlaylist(String playlistName) {
		ArrayList<Playlist> newList = new ArrayList<Playlist>();
		
		for (Playlist p : playlists) {
			if (p.getName().toLowerCase().equals(playlistName.toLowerCase())) {
				newList.add(p);
			}
		}
		
		return newList;
	}
	/*
	 * Method: hasAlbum(title)
	 * Purpose: check if an album with name title is found
	 * in the albumList
	 */
	private boolean hasAlbum(String title) {
		
		for (Album a : albumList) {
			if (a.getAlbumName().toLowerCase().equals(title.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Method: hasSong(title)
	 * Purpose: check if an album with name title is found
	 * in the albumList
	 */
	private boolean hasSong(Album a, String title) {
		
		for (Song s : a.getSongs()) {
			if (s.getName().toLowerCase().equals(title.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Method: addSong(title)
	 * Purpose: add a song to the user library into the appropriate album,
	 * make the album if there isn't one already
	 */
	public boolean addSong(String title) {
		
		ArrayList<Song> foundSongs = store.searchSong(title);
		
		if (foundSongs.size() == 0) {
			return false;
		}
		
		Song foundSong = foundSongs.get(0);
		
		// If the album isn't in our library, we make it
		if (!hasAlbum(foundSong.getAlbum())) {
			for (Album a : store.getAlbums()) {
				if (a.getAlbumName().equals(foundSong.getAlbum())) {
					albumList.add(new Album(a.getAuthorName(), a.getAlbumName(), a.getYear(), a.getGenre()));
				}
			}
		}
		
		for (Album a : albumList) {
			if (a.getAlbumName().equals(foundSong.getAlbum())) {
				if (!hasSong(a, foundSong.getName())) {
					a.addSong(foundSong);
					return true;
				} 
			}
		}
		
		return false;
	}
	
	/*
	 * Method: addAlbum(title)
	 * Purpose: add an entire album to the user's library, if its in the store
	 * and not already in the user's library
	 */
	public boolean addAlbum(String title) {
		
		ArrayList<Album> foundAlbums = store.searchAlbum(title);
		
		if (foundAlbums.size() == 0) {
			return false;
		}
		
		Album foundAlbum = foundAlbums.get(0);
		
		for (Album a : albumList) {
			if (a.getAlbumName().equals(foundAlbum.getAlbumName())) {
				for (Song s : foundAlbum.getSongs()) {
					if (!hasSong(a, s.getName())) {
						a.addSong(s);
					}
				}
				return true;
			}
		}
		
		Album newAlbum = new Album(foundAlbum.getAuthorName(), foundAlbum.getAlbumName(), foundAlbum.getYear(), foundAlbum.getGenre());
		albumList.add(newAlbum);
		
		for (Song s : foundAlbum.getSongs()) {
			newAlbum.addSong(s);
		}
		
		return true;
	}
	
	/*
	 * Method: getSongTitles()
	 * Purpose: print to the user a list of song titles
	 */
	public ArrayList<String> getSongTitles() {

		ArrayList<String> songs = new ArrayList<String>();
		
		for (Album a : albumList) {
			for (Song s : a.getSongs()) {
				songs.add(s.getName());
			}
		}
		
		return songs;
	}
	
	/*
	 * Method: getArtists()
	 * Purpose: print to the user a list of artists
	 */
	public ArrayList<String> getArtists() {
		ArrayList<String> printedArtists = new ArrayList<String>();
		
		for (Album a : albumList) {
			if (!printedArtists.contains(a.getAuthorName())) {
				printedArtists.add(a.getAuthorName());
			}
		}
		
		return printedArtists;
	}
	
	/*
	 * Method: getAlbums()
	 * Purpose: print to the user a list of albums
	 */
	public ArrayList<Album> getAlbums() {
		
		ArrayList<Album> newList = new ArrayList<Album>();

		for (Album a : albumList) {
			newList.add(new Album(a.getAuthorName(), a.getAlbumName(), a.getYear(), a.getGenre()));
			for (Song s : a.getSongs()) {
				a.addSong(s);
			}
		}
		
		return newList;
	}
	
	/*
	 * Method: getPlaylists()
	 * Purpose: print to the user their list of playlists
	 */
	public ArrayList<String> getPlaylists() {
		
		ArrayList<String> playlistNames = new ArrayList<String>();
		
		for (Playlist p : playlists) {
			playlistNames.add(p.getName());
		}
		
		return playlistNames;
	}
	
	/*
	 * Method: getSongsInPlaylist(title)
	 * Purpose: print out the songs in a playlist
	 */
	public ArrayList<String> getSongsInPlaylist(String title) {
		
		ArrayList<String> songs = new ArrayList<String>();
		
		for (Playlist p : playlists) {
			if (p.getName().toLowerCase().equals(title.toLowerCase())) {
				for (Song s : p.getSongs()) {
					songs.add(s.getName());
				}
			}
		}
		
		return songs;
	}
	
	/*
	 * Method: getFavoriteSongs()
	 * Purpose: print out the users favorite songs
	 */
	public ArrayList<String> getFavoriteSongs() {
		
		ArrayList<String> favSongs = new ArrayList<String>();
		
		for (Album a : albumList) {
			for (Song s : a.getSongs()) {
				if (s.isFavorite()) {
					favSongs.add(s.getName());
				}
			}
		}
		
		return favSongs;
	}
	
	/*
	 * Method: createPlaylist(name)
	 * Purpose: creates a new playlist with name
	 */
	public boolean createPlaylist(String name) {
		
		for (Playlist p : playlists) {
			if (p.getName().toLowerCase().equals(name.toLowerCase())) {
				return false;
			}
		}
		
		playlists.add(new Playlist(name));
		return true;
	}
	
	/*
	 * Method: removePlaylist(name)
	 * Purpose: removes a playlist with name
	 */
	public boolean removePlaylist(String name) {
		for (Playlist p : playlists) {
			if (p.getName().toLowerCase().equals(name.toLowerCase())) {
				playlists.remove(p);
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * Method: addToPlaylist
	 * Purpose: add a song by title and artist into a playlist
	 */
	public boolean addToPlaylist(String playlist, String name, String artist) {
		ArrayList<Song> foundSongs = this.searchSongTitle(name);
		
		Playlist foundList = null;
		for (Playlist p : playlists) {
			if (p.getName().toLowerCase().equals(playlist.toLowerCase())) {
				foundList = p;
			}
		}
		
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
	 * Method: removeFromPlaylist
	 * Purpose: remove a song by title from a playlist
	 */
	public boolean removeFromPlaylist(String playlist, String name) {
		
		for (Playlist p : playlists) {
			if (p.getName().toLowerCase().equals(playlist.toLowerCase())) {
				return p.removeSong(name);
			}
		}
		
		return false;
	}
	
	/*
	 * Method: rateSong
	 * Purpose: search for a song by title and artist and assign it a rating
	 */
	public boolean rateSong(String songName, String artist, int rating) {
		
		for (Album a : albumList) {
			if (a.getSong(songName) != null) {
				if (a.getSong(songName).getAuthor().toLowerCase().equals(artist.toLowerCase())) {
					return a.rateSong(songName, rating);
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Method: setFavorite()
	 * Purpose: search for a song by title and artist and set to favorite or unfavorite
	 */
	public boolean setFavorite(String songName, String artist, boolean favorite) {

		for (Album a : albumList) {
			if (a.getSong(songName) != null) {
				if (a.getSong(songName).getAuthor().toLowerCase().equals(artist.toLowerCase())) {
					return a.setFavorite(songName, favorite);
				}
			}
		}
		
		return false;
	}
	
	public MusicStore getStore() {
		return this.store;
	}
}
