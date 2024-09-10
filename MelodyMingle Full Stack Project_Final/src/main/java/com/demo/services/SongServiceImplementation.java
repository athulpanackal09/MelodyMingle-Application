package com.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Song;
import com.demo.repositories.SongRepository;

@Service
public class SongServiceImplementation implements SongService{

	@Autowired
	SongRepository songrepo;
	
	@Override
	public String addSongs(Song song) {
		songrepo.save(song);
		return "Song added";
	}

	@Override
	public boolean nameExists(String name) {
		if(songrepo.findByName(name)==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public List<Song> viewSong() {
		
		List<Song> songlist=songrepo.findAll();
		return songlist;
	}

	@Override
	public void updateSong(Song song) {

		songrepo.save(song);
		
	}

}

