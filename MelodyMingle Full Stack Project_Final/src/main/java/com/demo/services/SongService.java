package com.demo.services;

import java.util.List;

import com.demo.entities.Song;

public interface SongService {

	public String addSongs(Song song);
	public boolean nameExists(String name);
	public List<Song> viewSong();
	public void updateSong(Song song);
	
}
