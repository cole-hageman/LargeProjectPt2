package model;

public class Song {
	private String songName;
	private String authorName;
	private String album;
	private String genre;
	private int rating;
	private boolean favorite;
	private int timesPlayed;

	public Song(String songName,String authorName,String album,String Genre) {
		this.album=album;
		this.authorName=authorName;
		this.songName = songName;
		this.rating = 0;
		this.favorite=false;
		this.timesPlayed=0;
		this.genre=Genre;
		
	}
	
	public String getGenre() {
		return genre;
	}
	
	public int getTimesPlayed() {
		return timesPlayed;
	}
	
	public void playSong() {
		timesPlayed++;
	}
	


	public void setFavorite(boolean value) {
		this.favorite = value;
	}
	
	/*
	 * sets rating to value
	 */
	public void setRating(int value) {
		if(value==5) {
			favorite=true;
		}
		rating=value;
		
		
	}
	public boolean isFavorite() {
		return favorite;
	}

	public int getRating() {
		return rating;
	}

	public String getName() {
		return songName;

	}
	public String getAlbum() {
		return album;
	}
	
	public String getAuthor() {
		return authorName;
	}

	public String toString() {
		return "Song name: '"+songName+ "'. Author: '"+authorName+"'. Album Name '"+ album + "'";

	}
	
	@Override
	public boolean equals(Object s) {
		Song song=(Song) s;
		
		if(song.getAlbum()==album && song.getAuthor()==authorName && song.getName()==songName) {
			return true;
		}
		return false;
	}



	

}
