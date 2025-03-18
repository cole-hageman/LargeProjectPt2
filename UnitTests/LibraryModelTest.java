package UnitTests;

import model.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class LibraryModelTest {

	private LibraryModel userLib;
	
	@BeforeEach
	void setUp() {
		userLib = new LibraryModel();
	}
	
	// ADD SONG
	@Test
	void addSong1() {
		assertTrue(userLib.addSong("Clocks"));
		assertTrue(userLib.addSong("tired"));
	}
	
	@Test
	void addSong2() {
		assertFalse(userLib.addSong("DEFG"));
		assertFalse(userLib.addSong("adele"));
	}
	
	@Test
	void addSong3() {
		assertTrue(userLib.addSong("Clocks"));
		assertFalse(userLib.addSong("clocks"));
	}
	
	// ADD ALBUM
	@Test
	void addAlbum1() {
		assertTrue(userLib.addAlbum("Old Ideas"));
		assertTrue(userLib.addAlbum("Waking Up"));
	}
	
	@Test
	void addAlbum2() {
		assertFalse(userLib.addAlbum("DEFG"));
		assertFalse(userLib.addAlbum("adele"));
	}
	
	@Test
	void addAlbum3() {
		assertTrue(userLib.addSong("Tired"));
		assertTrue(userLib.addSong("Lovesong"));
		assertTrue(userLib.addAlbum("19"));
		assertTrue(userLib.addAlbum("21"));
	}
	
	// SearchSongTitle
	@Test
	void testSearchSongTitle1() {
		userLib.addSong("Tired");
		ArrayList<Song> foundSongs = userLib.searchSongTitle("Tired");
		assertEquals(1, foundSongs.size());
	}
	
	@Test
	void testSearchSongTitle2() {
		userLib.addAlbum("Old Ideas");
		userLib.addAlbum("Waking Up");
		ArrayList<Song> foundSongs = userLib.searchSongTitle("Lullaby");
		assertEquals(2, foundSongs.size());
	}
	
	@Test
	void testSearchSongTitleLowerCase() {
		userLib.addSong("Tired");
		ArrayList<Song> foundSongs = userLib.searchSongTitle("tired");
		assertEquals(1, foundSongs.size());
	}
	
	@Test
	void testSearchSongTitleInvalid() {
		userLib.addSong("tired");
		ArrayList<Song> foundSongs = userLib.searchSongTitle("abdf");
		assertEquals(0, foundSongs.size());
	}
	
	
	// SearchSongArtist
	@Test
	void testSearchSongArtist1() {
		userLib.addSong("clocks");
		ArrayList<Song> foundSongs = userLib.searchSongArtist("Coldplay");
		assertEquals(1, foundSongs.size());
	}
	
	@Test
	void testSearchSongArtist2() {
		userLib.addSong("tired");
		userLib.addSong("lovesong");
		ArrayList<Song> foundSongs = userLib.searchSongArtist("Adele");
		assertEquals(2, foundSongs.size());
	}
	
	@Test
	void testSearchSongArtistLowerCase() {
		userLib.addSong("Fire");
		ArrayList<Song> foundSongs = userLib.searchSongArtist("the heavy");
		assertEquals(1, foundSongs.size());
	}
	
	@Test
	void testSearchSongArtistInvalid() {
		userLib.addSong("tired");
		ArrayList<Song> foundSongs = userLib.searchSongArtist("Cole Hageman");
		assertEquals(0, foundSongs.size());
	}
	
	
	// SearchAlbumTitle
	@Test
	void testSearchAlbumTitle1() {
		userLib.addSong("Tired");
		ArrayList<Album> foundAlbums = userLib.searchAlbumTitle("19");
		assertEquals(1, foundAlbums.size());
	}
	
	@Test
	void testSearchAlbumTitleInvalid() {
		userLib.addSong("tired");
		ArrayList<Album> foundAlbums = userLib.searchAlbumTitle("abdf");
		assertEquals(0, foundAlbums.size());
	}
	
	
	// SearchSongArtist
	@Test
	void testSearchAlbumArtist1() {
		userLib.addSong("clocks");
		ArrayList<Album> foundAlbums = userLib.searchAlbumArtist("Coldplay");
		assertEquals(1, foundAlbums.size());
	}
	
	@Test
	void testSearchAlbumArtist2() {
		userLib.addSong("tired");
		userLib.addSong("lovesong");
		ArrayList<Album> foundAlbums = userLib.searchAlbumArtist("Adele");
		assertEquals(2, foundAlbums.size());
	}
	
	@Test
	void testSearchAlbumArtistLowerCase() {
		userLib.addSong("Fire");
		ArrayList<Album> foundAlbums = userLib.searchAlbumArtist("the heavy");
		assertEquals(1, foundAlbums.size());
	}
	
	@Test
	void testSearchAlbumArtistInvalid() {
		userLib.addSong("tired");
		ArrayList<Album> foundAlbums = userLib.searchAlbumArtist("Cole Hageman");
		assertEquals(0, foundAlbums.size());
	}
	
	
	@Test
	void testGetSongTitles() {
		assertEquals(0, userLib.getSongTitles().size());
		userLib.addSong("Tired");
		userLib.addSong("lovesong");
		userLib.addSong("clocks");
		assertEquals(3, userLib.getSongTitles().size());
		userLib.addSong("Fire");
		assertEquals(4, userLib.getSongTitles().size());
	}
	
	@Test
	void testGetArtists() {
		assertEquals(0, userLib.getArtists().size());
		userLib.addSong("Tired");
		userLib.addSong("lovesong");
		userLib.addSong("clocks");
		assertEquals(2, userLib.getArtists().size());
		userLib.addSong("Fire");
		assertEquals(3, userLib.getArtists().size());
	}
	
	@Test
	void testGetAlbums() {
		assertEquals(0, userLib.getAlbums().size());
		userLib.addSong("Tired");
		userLib.addSong("lovesong");
		userLib.addSong("clocks");
		assertEquals(3, userLib.getAlbums().size());
		userLib.addSong("Fire");
		assertEquals(4, userLib.getAlbums().size());
	}
	
	@Test
	void testCreatePlaylist1() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.createPlaylist("NextMix"));
	}
	
	@Test
	void testCreatePlaylist2() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertFalse(userLib.createPlaylist("newmix"));
		assertFalse(userLib.createPlaylist("NewMix"));
	}
	
	@Test
	void testGetPlaylists1() {
		assertEquals(0, userLib.getPlaylists().size());
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.createPlaylist("NextMix"));
		assertEquals(2, userLib.getPlaylists().size());
		assertTrue(userLib.createPlaylist("NextMixes"));
		assertEquals(3, userLib.getPlaylists().size());
	}
	
	@Test
	void testRemovePlaylist1() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.createPlaylist("NextMix"));
		assertTrue(userLib.createPlaylist("NextMixes"));
		assertEquals(3, userLib.getPlaylists().size());
		assertTrue(userLib.removePlaylist("newmix"));
		assertEquals(2, userLib.getPlaylists().size());
	}
	
	@Test
	void testRemovePlaylist2() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.createPlaylist("NextMix"));
		assertTrue(userLib.createPlaylist("NextMixes"));
		assertEquals(3, userLib.getPlaylists().size());
		assertFalse(userLib.removePlaylist("myMix"));
		assertEquals(3, userLib.getPlaylists().size());
	}
	
	@Test
	void testAddToPlaylist1() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.addSong("Tired"));
		assertTrue(userLib.addToPlaylist("NewMix", "Tired", "Adele"));
	}
	
	@Test
	void testAddToPlaylist2() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.addSong("Tired"));
		assertFalse(userLib.addToPlaylist("NewMix", "Lovesong", "Adele"));
		assertFalse(userLib.addToPlaylist("myMix", "Tired", "Adele"));
	}
	
	@Test
	void testSearchPlaylist() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertEquals(1, userLib.searchPlaylist("NewMix").size());
		assertEquals(0, userLib.searchPlaylist("myMix").size());
	}
	
	@Test
	void testGetSongsInPlaylist() {
		assertTrue(userLib.createPlaylist("NewMix"));
		userLib.addAlbum("19");
		assertEquals(0, userLib.getSongsInPlaylist("newmix").size());
		Album foundAlbum = userLib.searchAlbumTitle("19").get(0);
		for (Song s : foundAlbum.getSongs()) {
			userLib.addToPlaylist("newmix", s.getName(), "Adele");
		}
		assertEquals(12, userLib.getSongsInPlaylist("newmix").size());
	}
	
	@Test
	void testSetFavorite() {
		userLib.addAlbum("19");
		assertTrue(userLib.setFavorite("Tired", "Adele", true));
		assertFalse(userLib.setFavorite("Tired", "Moana", true));
	}
	
	@Test
	void testRemoveFromPlaylist() {
		assertTrue(userLib.createPlaylist("NewMix"));
		userLib.addAlbum("19");
		Album foundAlbum = userLib.searchAlbumTitle("19").get(0);
		for (Song s : foundAlbum.getSongs()) {
			userLib.addToPlaylist("newmix", s.getName(), "Adele");
		}
		assertEquals(12, userLib.getSongsInPlaylist("newmix").size());
		assertTrue(userLib.removeFromPlaylist("newmix", "tired"));
		assertEquals(11, userLib.getSongsInPlaylist("newmix").size());
		assertTrue(userLib.removeFromPlaylist("newmix", "my same"));
		assertEquals(10, userLib.getSongsInPlaylist("newmix").size());
		assertFalse(userLib.removeFromPlaylist("newmix", "my name"));
		assertFalse(userLib.removeFromPlaylist("myMix", "my name"));
		assertEquals(10, userLib.getSongsInPlaylist("newmix").size());
	}
	
	@Test
	void testRateSong() {
		userLib.addAlbum("19");
		assertTrue(userLib.rateSong("Tired", "Adele", 4));
		assertTrue(userLib.rateSong("Tired", "Adele", 5));
		assertFalse(userLib.rateSong("Tires", "Adele", 5));
		assertFalse(userLib.rateSong("Tired", "ADELLLE", 5));
	}
	
	@Test
	void testGetFavoriteSongs() {
		userLib.addAlbum("19");
		assertTrue(userLib.rateSong("Tired", "Adele", 5));
		assertTrue(userLib.setFavorite("my same", "adele", true));
		assertEquals(2, userLib.getFavoriteSongs().size());
		assertTrue(userLib.getFavoriteSongs().get(0).equals("My Same"));
	}
}
