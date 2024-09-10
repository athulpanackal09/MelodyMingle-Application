package com.demo.services;

import java.util.List;

import com.demo.entities.Playlist;

public interface PlayListService {

	public void addPlaylist(Playlist playlist);

	public List<Playlist> fetchPlaylists();

}
