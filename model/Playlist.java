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
	
	public boolean removeSong(String title) {
		for (Song s : songs) {
			if (s.getName().toLowerCase().equals(title.toLowerCase())) {
				songs.remove(s);
				return true;
			}
		}
		return false;
	}
}
