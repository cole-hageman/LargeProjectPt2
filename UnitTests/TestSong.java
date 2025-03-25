package UnitTests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class TestSong {
	
	private Song Song1;
	private Song Song2;
	private Song Song3;
	private Song Song4;
	private Song Song5;
	private Song Song6;
	
	@BeforeEach
    void setUp() {
        Song1=new Song("Tu si","Tito","Incomodo", "a");
        Song2=new Song("ella","Junior h","CT", "a");
        Song3=new Song("Tu si","Tito","Incomodo", "a");
        Song4=new Song("Tu si","Genesis","Incomodo", "a");
        Song5=new Song("Tu si","Genesis","Pop", "a");
        Song6=new Song("Tu si","Tito","CT", "a");
    }
	
	

	@Test
	void TestIsFavorite() {
		Song1.setFavorite(true);
		 Assertions.assertTrue(Song1.isFavorite());
		 
		
	}
	
	@Test 
	void TestToString() {
		String message="";
		 message+="Song name: '"+"Tu si"+ "'. Author: '"+"Tito"+"'. Album Name '"+ "Incomodo" + "'";
		 Assertions.assertEquals(message,Song1.toString());
		
	}
	
	@Test
	void getRating() {
		Assertions.assertEquals(Song1.getRating(), 0);
	}
	@Test
	void getName() {
		Assertions.assertEquals(Song1.getName(), "Tu si");
	}
	@Test
	void getAuthor() {
		Assertions.assertEquals(Song1.getAuthor(), "Tito");
	}
	
	@Test 
	void testEquals() {
		Assertions.assertTrue(Song1.equals(Song3));
	}
	
	@Test 
	void testEquals2() {
		Assertions.assertFalse(Song1.equals(Song4));
		Assertions.assertFalse(Song1.equals(Song5));
		Assertions.assertFalse(Song2.equals(Song6));
	}
	
	@Test 
	void setRating() {
		Song1.setRating(5);
		Song2.setRating(2);
		
		Assertions.assertEquals(Song1.getRating(),5);
		Assertions.assertEquals(Song2.getRating(),2);
	}


}
