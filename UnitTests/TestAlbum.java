package UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class TestAlbum {
	
	private Album TitoAlbum;
	private Album PesoAlbum;
	private Album copyAlbum;
	private Album similarAlbum;
	private Album similarAlbum2;
	private Album similarAlbum3;
	private Song Song1;
	private Song Song2;
	private Song Song3;
	private Song Song4;
	private Song Song5;
	
	@BeforeEach
    void setUp() {
        TitoAlbum=new Album("Tito","Incomodo",2024,"pop");
        PesoAlbum=new Album("Peso","Genesis",2024,"pop");
        copyAlbum=new Album("Tito","Incomodo",2024,"pop");
        similarAlbum=new Album("Tito", "Genesis", 2024, "pop");
        similarAlbum2=new Album("Tito", "Genesis", 2023, "pop");
        similarAlbum3=new Album("Tito", "Genesis", 2023, "rock");
        Song1=new Song("Tu si","Tito","Incomodo");
        Song2=new Song("Gervonta","Peso","Genesis");
        Song3=new Song("Hollywood","Peso","Genesis");
        Song4=new Song("ella","Tito","Incomodo");
        Song5=new Song("tu","Tito","Incomodo");
        TitoAlbum.addSong(Song1);
        TitoAlbum.addSong(Song4);
        copyAlbum.addSong(Song1);
        copyAlbum.addSong(Song4);
        PesoAlbum.addSong(Song2);
        PesoAlbum.addSong(Song3);
        
   
        
        
    }

	@Test
	void testToString() {
		String titoString=PesoAlbum.toString();
		String message="";
		message+="Album : '" + "Genesis" + "' by '"+"Peso"+ "', Genre '"+"pop"+
		"'. From the year : "+ 2024+"\n";
		message+=Song2.getName()+"\n"+Song3.getName()+"\n";
		Assertions.assertEquals(message,titoString);
	
		
		
	}
	@Test
	void testGetSong() {
		Song titos=TitoAlbum.getSong("Tu si");
		Assertions.assertEquals(titos,Song1);
		Song notThere=TitoAlbum.getSong("hello world");
		Assertions.assertNull(notThere);
		
		
	}
	
	@Test
	void getAlbumName() {
		Assertions.assertEquals(PesoAlbum.getAlbumName(),"Genesis");
		
	}
	
	@Test
	void TestGetGenre() {
		Assertions.assertEquals(PesoAlbum.getGenre(),"pop");
		
	}
	
	@Test
	void Testyear() {
		Assertions.assertEquals(PesoAlbum.getYear(),2024);
		
	}
	
	@Test 
	void getSongs() {
		ArrayList<Song> pesoSongs=PesoAlbum.getSongs();
		Assertions.assertEquals(pesoSongs.get(0),Song2);
		Assertions.assertEquals(pesoSongs.get(1),Song3);
		
		
	}
	
	@Test 
	void testFavorite() {
		boolean value=PesoAlbum.setFavorite("Hollywood", true);
		boolean notvalue=PesoAlbum.setFavorite("other", true);
		Assertions.assertFalse(notvalue);
		Assertions.assertTrue(value);
		
	}
	
	@Test 
	void testRateSong() {
		boolean value=PesoAlbum.rateSong("Hollywood", 5);
		boolean notvalue=PesoAlbum.rateSong("other", 3);
		Assertions.assertFalse(notvalue);
		Assertions.assertTrue(value);
		

		
	}
	@Test
	void testEquals() {
		copyAlbum.addSong(Song5);
		assertFalse(TitoAlbum.equals(copyAlbum));
		TitoAlbum.addSong(Song5);
		assertTrue(TitoAlbum.equals(copyAlbum));
	}

	@Test
	void testEquals2() {
		copyAlbum.addSong(Song5);
		TitoAlbum.addSong(Song3);
		assertFalse(TitoAlbum.equals(copyAlbum));
	}
	
	@Test
	void testEquals3() {
		assertFalse(TitoAlbum.equals(similarAlbum));
		assertFalse(TitoAlbum.equals(similarAlbum2));
		assertFalse(similarAlbum.equals(similarAlbum3));
	}
}
