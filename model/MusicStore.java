package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MusicStore {
	private ArrayList<Album> albums;
	
	
	public MusicStore() {
		
		albums=new ArrayList<Album>();
		//creates a the lists of filenames from given file
		ArrayList<File> curr=new ArrayList<File>();
		try {
			curr=processAlbums("src/albumFiles/albums.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (File file: curr) {
			try {
				//reads all of the files and creates the albums
				readAlbumFile(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	/*
	 * Search the songs by name and return a list of the
	 * song objects with the same name
	 */
	
	public ArrayList<Song> searchSong(String songName) {
		ArrayList<Song> foundSongs = new ArrayList<Song>();
		for(Album songs: albums) {
			if(songs.getSong(songName)!=null) {
				foundSongs.add(songs.getSong(songName));
			}
		}
		return foundSongs;
	}
	
	/*
	 *Gets the list of albums and returns a deep copy
	 */

	public ArrayList<Album> getAlbums(){
		ArrayList<Album> deepCopy=new ArrayList<Album>();
		for(Album album: albums) {
			Album current=new Album(album.getAuthorName(),album.getAlbumName(),
					album.getYear(),album.getGenre());
			for(Song s: album.getSongs()) {
				current.addSong(s);
			}
			deepCopy.add(current);
			
		}
		return deepCopy;
	}
	
	/*
	 * Search the Album by name and return a list of the
	 * Album objects with the same name
	 */
	
	public ArrayList<Album> searchAlbum(String albumName) {
		ArrayList<Album> foundAlbums = new ArrayList<Album>();
		
		for (Album album: albums) {
			if(album.getAlbumName().toLowerCase().equals(albumName.toLowerCase())) {
				Album current=new Album(album.getAuthorName(),album.getAlbumName(),
						album.getYear(),album.getGenre());
				for(Song s: album.getSongs()) {
					current.addSong(s);
					
				}
				foundAlbums.add(current);
			}
		}
		return foundAlbums;
		
	}
	
	/*
	 * Reads input from the file and converts into and album
	 */
	
	public void readAlbumFile(File fileName) throws FileNotFoundException {
		Scanner scan=new Scanner(fileName);
		//reads the first line gets album title,artist,genre,year
		String [] attributes= scan.nextLine().split(",");
		String albumTitle=attributes[0];
		String Artist=attributes[1]; 
		String genre=attributes[2];
		int year=Integer.parseInt(attributes[3]);
		Album current=new Album(Artist,albumTitle,year,genre);
		while(scan.hasNextLine()) {
			current.addSong(new Song(scan.nextLine().trim(),Artist,albumTitle,genre));
		}
		
		albums.add(current);
		
		
	}
	
	/*
	 * searches song by artist and returns an arrayList of songs
	 * with the same name
	 */
	
	public ArrayList<Song> searchSongArtist(String artist) {
		
		ArrayList<Song> foundSongs = new ArrayList<Song>();
		for (Album a : albums) {
			if (a.getAuthorName().toLowerCase().equals(artist.toLowerCase())) {
				for (Song s : a.getSongs()) {
					foundSongs.add(s);
				}
			}
		}

		return foundSongs;
	}
	
	/*
	 * search song by artist and return a list of 
	 * albums that share the artist names
	 */
	
	
    public ArrayList<Album> searchAlbumArtist(String artistName) {
		
		ArrayList<Album> foundAlbums = new ArrayList<Album>();
		for (Album a : albums) {
			if (a.getAuthorName().toLowerCase().equals(artistName.toLowerCase())) {
				foundAlbums.add(a);
			}
		}
		
		return foundAlbums;
	}
    
    /*
     * processes A file that contains the author and the name of
     * the album and converts into a text file to acess its data later
  
     */
	
	public ArrayList<File> processAlbums(String fileName) throws FileNotFoundException {
		ArrayList<File> fileNames=new ArrayList<File>();
		File file=new File(fileName);
		Scanner scan=new Scanner(file);
		while(scan.hasNextLine()) {
			String[] names=scan.nextLine().split(",");
			fileNames.add(new File("src/albumFiles/"+names[0]+"_"+names[1]+".txt"));
		}
		return fileNames;
		
		
	}
	
}
