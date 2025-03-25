package model;
import java.util.*;

public class Playlist {
	
	private ArrayList<Song> songs;
	private String name;
	
	public Playlist(String name) {
		this.name = name;
		songs = new ArrayList<Song>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Song> getSongs() {
		return new ArrayList<Song>(songs);
	}
	
	public void addSong(Song s) {
		songs.add(s);
	}
	
	/*
	 * Removes a song by the title of the playlist
	 * 
	 */
	
	public boolean removeSong(String title) {
		for (int i = 0; i < songs.size(); i++) {
			Song s = songs.get(i);
			if (s.getName().equals(title) ) {
				songs.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public void removeFirst() {
		if (!songs.isEmpty()) {
			songs.remove(0);
		}
	}

	
	public int getSize() {
		return songs.size();
	}
	
	public String toString() {
		String message=""+name;
		for(Song s:songs) {
			message+=s+"\n";
		}
		return message;
	
		
	}
}
