package com.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Playlist;
import com.demo.repositories.PlayListRepository;

@Service
public class PlayListServiceImplementation implements PlayListService {
	
	@Autowired
	PlayListRepository prepo;

	@Override
	public void addPlaylist(Playlist playlist) {
		// TODO Auto-generated method stub
		prepo.save(playlist);
		
		
	}

	@Override
	public List<Playlist> fetchPlaylists() {
		// TODO Auto-generated method stub
		return prepo.findAll();
	}
	

}