package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Album {
	private String authorName;
	private String albumName;
	private final int year;
	private String genre;
	private HashMap<String,Song> songsCollection;


	public Album(String authorName, String albumName,int year,String genre) {
		this.genre=genre;
		this.authorName = authorName;
		this.albumName = albumName;
		this.year=year;
		
		songsCollection = new HashMap<String,Song>();

	}
	
	
  
	public void addSong(Song song) {
		songsCollection.put(song.getName(),song);

	}

    /*
     * returns the album as a string as albumName authorName Genre Year
     * list of songs
     */
   
	public String toString() {
		String message = "";
		message += "Album : '" + albumName + "' by '"+ authorName+ "', Genre '"+genre+
				"'. From the year : "+ year+"\n" ;
		for (String song : songsCollection.keySet()) {
			message += song + "\n";
		}
		return message;

	}
	
	/*
	 * gets the song by name and returns a copy of the 
	 * song object . null if song not found 
	 * 
	 */

	public Song getSong(String songName) {
		Song current= songsCollection.get(songName);
		Song newSong = new Song(current.getName(),current.getAuthor(),current.getAlbum());
		newSong.setFavorite(current.isFavorite());
		newSong.setRating(current.getRating());
		return newSong;

		
		

	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public int getYear() {
		return year;
	}
	
	/*
	 * returns a deep copy of the arrayList of songs
	 */
	public ArrayList<Song> getSongs(){
		ArrayList<Song>curr=new ArrayList<Song>();
		for(Song s: songsCollection.values()) {
			Song newSong=new Song(s.getName(),s.getAuthor(),s.getAlbum());
			newSong.setFavorite(s.isFavorite());
			newSong.setRating(s.getRating());
			curr.add(newSong);
		}
		return curr;
	}
	
	/*
	 * sets a song to favorite or unfavorite given 
	 * a boolean value return true if value was set
	 * false if song not found 
	 */
	
	public boolean setFavorite(String songName, boolean favorite) {
		if(songsCollection.get(songName)==null) {
			return false;
		}
		else {
			Song current=songsCollection.get(songName);
			current.setFavorite(favorite);
			return true;
			
		}
		
		
	}
	
	/*
	 * rates a song given the song name and rating
	 * return true if was able to set false if
	 * song not found 
	 */
	
	public boolean rateSong(String songName, int rating) {
		if(songsCollection.get(songName)==null) return false;
		else {
			Song current=songsCollection.get(songName);
			current.setRating(rating);
			return true;
			
			
		}
		
		
	}
	
	@Override
	
	/*
	 * checks if one album is the same as other album
	 */
	public boolean equals(Object obj) {
	    Album other = (Album) obj;
	    
	    if (albumName.equals(other.getAlbumName()) && authorName.equals(other.getAuthorName()) &&
	        genre.equals(other.getGenre()) && year == other.getYear() &&
	        songsCollection.size() == other.getSongs().size()) {
	        for (int i = 0; i< songsCollection.size(); i++) {
	            if (!songsCollection.get(i).equals(other.getSongs().get(i))) {
	                return false;
	            }
	        }
	        return true; 
	    	}

	    return false; 
			}

}