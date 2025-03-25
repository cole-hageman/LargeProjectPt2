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
		if (!songs.contains(s)) {
			songs.add(s);
		}
	}
	
	/*
	 * Removes a song by the title of the playlist
	 * 
	 */
	
	public boolean removeSong(String title) {
		for (Song s : songs) {
			if (s.getName().toLowerCase().equals(title.toLowerCase())) {
				songs.remove(s);
				return true;
			}
		}
		return false;
	}
	
	public void removeFirst() {
		songs.remove(0);
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
