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
		userLib = new LibraryModel("test");
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
		assertTrue(userLib.createPlaylist("newmix"));
		assertFalse(userLib.createPlaylist("NewMix"));
	}
	
	@Test
	void testGetPlaylists1() {
		assertEquals(4, userLib.getPlaylists().size());
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.createPlaylist("NextMix"));
		assertEquals(6, userLib.getPlaylists().size());
		assertTrue(userLib.createPlaylist("NextMixes"));
		assertEquals(7, userLib.getPlaylists().size());
	}
	
	@Test
	void testRemovePlaylist1() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.createPlaylist("NextMix"));
		assertTrue(userLib.createPlaylist("NextMixes"));
		assertEquals(7, userLib.getPlaylists().size());
		assertTrue(userLib.removePlaylist("NewMix"));
		assertEquals(6, userLib.getPlaylists().size());
	}
	
	@Test
	void testRemovePlaylist2() {
		assertTrue(userLib.createPlaylist("NewMix"));
		assertTrue(userLib.createPlaylist("NextMix"));
		assertTrue(userLib.createPlaylist("NextMixes"));
		assertEquals(7, userLib.getPlaylists().size());
		assertFalse(userLib.removePlaylist("myMix"));
		assertEquals(7, userLib.getPlaylists().size());
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
		assertEquals(0, userLib.getSongsInPlaylist("NewMix").size());
		Album foundAlbum = userLib.searchAlbumTitle("19").get(0);
		for (Song s : foundAlbum.getSongs()) {
			userLib.addToPlaylist("NewMix", s.getName(), "Adele");
		}
		assertEquals(12, userLib.getSongsInPlaylist("NewMix").size());
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
			assertTrue(userLib.addToPlaylist("NewMix", s.getName(), "Adele"));
		}
		assertEquals(12, userLib.getSongsInPlaylist("NewMix").size());
		assertTrue(userLib.removeFromPlaylist("NewMix", "Tired"));
		assertEquals(11, userLib.getSongsInPlaylist("NewMix").size());
		assertTrue(userLib.removeFromPlaylist("NewMix", "My Same"));
		assertEquals(10, userLib.getSongsInPlaylist("NewMix").size());
		assertFalse(userLib.removeFromPlaylist("NewMix", "My Name"));
		assertFalse(userLib.removeFromPlaylist("myMix", "My Name"));
		assertEquals(10, userLib.getSongsInPlaylist("NewMix").size());
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
		assertTrue(userLib.setFavorite("My Same", "Adele", true));
		assertEquals(2, userLib.getFavoriteSongs().size());
		assertTrue(userLib.getFavoriteSongs().get(0).equals("Tired"));
	}
	
	@Test
	void testGetFavoriteSongs2() {
		userLib.addAlbum("19");
		assertTrue(userLib.rateSong("Tired", "Adele", 5));
		assertTrue(userLib.setFavorite("My Same", "Adele", true));
		assertEquals(2, userLib.getFavoriteSongs().size());
		assertTrue(userLib.getFavoriteSongs().get(0).equals("Tired"));
		assertTrue(userLib.setFavorite("My Same", "Adele", false));
	}
	
	@Test
	void testCheckPW() {
		assertTrue(userLib.checkPassword("test"));
		assertFalse(userLib.checkPassword("notTest"));
	}
	
	@Test
	void testPlaySong1() {
		assertTrue(userLib.createPlaylist("NewMix"));
		userLib.addAlbum("19");
		assertTrue(userLib.playSong("Tired", "Adele"));
		assertFalse(userLib.playSong("Tred", "Adele"));
	}
	
	@Test
	void testPlaySong2() {
		assertTrue(userLib.createPlaylist("NewMix"));
		userLib.addAlbum("Old Ideas");
		userLib.addAlbum("Waking Up");
		assertFalse(userLib.playSong("Lullaby", "Adele"));
		assertTrue(userLib.playSong("Lullaby", "OneRepublic"));
		assertTrue(userLib.playSong("Marchin On", "OneRepublic"));
		assertTrue(userLib.playSong("Waking Up", "OneRepublic"));
		assertTrue(userLib.playSong("Fear", "OneRepublic"));
		assertTrue(userLib.playSong("Good Life", "OneRepublic"));
		assertTrue(userLib.playSong("All This Time", "OneRepublic"));
		assertTrue(userLib.playSong("Secrets", "OneRepublic"));
		assertTrue(userLib.playSong("Made for You", "OneRepublic"));
		assertTrue(userLib.playSong("Everybody Loves Me", "OneRepublic"));
		assertTrue(userLib.playSong("Missing Persons 1 & 2", "OneRepublic"));
		assertTrue(userLib.playSong("All the Right Moves", "OneRepublic"));
	}
	
	@Test
	void testRemoveSong1() {
		userLib.addAlbum("19");
		assertTrue(userLib.removeSong("Tired", "Adele"));
		assertFalse(userLib.removeSong("Tred", "Adele"));
	}
	
	@Test
	void testRemoveAlbum1() {
		userLib.addAlbum("19");
		assertTrue(userLib.removeAlbum("19"));
		assertFalse(userLib.removeAlbum("Tred"));
	}
	
	@Test
	void testGetSongsSortedRating() {
		userLib.addAlbum("19");
		assertTrue(userLib.rateSong("Tired", "Adele", 5));
		assertEquals(12, userLib.getSongsSortedRating().size());
	}
	
	@Test
	void testCheckHashedPW() {
		assertEquals(44, userLib.getHashedPw().length());
	}
	
	@Test
	void testGetSongsSortedName() {
		userLib.addAlbum("19");
		assertTrue(userLib.rateSong("Tired", "Adele", 5));
		assertEquals(12, userLib.sortSongsName().size());
	}
	
	@Test
	void testGetSongsSortedArtist() {
		userLib.addAlbum("19");
		assertTrue(userLib.rateSong("Tired", "Adele", 5));
		assertEquals(12, userLib.sortByArtist().size());
	}
	
	@Test
	void testShuffle() {
		userLib.addAlbum("19");
		assertEquals(12, userLib.shuffle().size());
	}
	
	@Test
	void testSearchByGenre() {
		userLib.addAlbum("19");
		assertEquals(12, userLib.searchGenre("Pop").size());
	}
	
}
