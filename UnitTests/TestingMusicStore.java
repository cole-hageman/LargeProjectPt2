package UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import model.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestingMusicStore {
	
	private MusicStore music; 
	private ArrayList<Album> items;
	

    @BeforeEach
    void setUp() {
        music= new MusicStore();
        items=music.getAlbums();
        
    }

	@Test
	void SearchAlbumByName() {
		
        Album adele = items.get(0);
        ArrayList<Album> adele2 = music.searchAlbum("19");
        Assertions.assertTrue(adele.equals(adele2.get(0)));
        
    }
	
	@Test
	void searchSongByTitle() {
        Album curr=items.get(0);
        Song r=curr.getSong("Tired");
        ArrayList<Song> adele = music.searchSong("Tired");
        Assertions.assertTrue(adele.get(0).equals(r));
		
	}
	
	@Test
	void getAlbums() {
		
        Album adele =items.get(0);
        ArrayList<Album> adele2 = music.searchAlbum("19");
        Assertions.assertTrue(adele.equals(adele2.get(0)));
        	
	}
	@Test
	void searchSongByArtist() {
		ArrayList<Song> adeleMusic=music.searchSongArtist("Alabama Shakes");
		ArrayList<Song> curr=items.get(3).getSongs();
		 Assertions.assertTrue(curr.equals(adeleMusic));
			
	}
	
	@Test
	void searchAlbumByArtist() {
		ArrayList<Album> songs=music.searchAlbumArtist("Adele");
		
		Album adele1=songs.get(0);
		Album adele2=songs.get(1);
		 Assertions.assertTrue(adele1.equals(items.get(0)));
		 Assertions.assertTrue(adele2.equals(items.get(1)));
	}
	
	
	
}
	
	
