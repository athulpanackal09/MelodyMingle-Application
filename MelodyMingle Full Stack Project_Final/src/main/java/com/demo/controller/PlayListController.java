package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.entities.Playlist;
import com.demo.entities.Song;
import com.demo.services.PlayListService;
import com.demo.services.SongService;

@Controller
public class PlayListController {

	@Autowired
	PlayListService pserv;

	@Autowired
	SongService sserv;

	@GetMapping("/createplaylist")
	public String createPlayList(Model model) {

		//Fetching the songs using song service
		List<Song> songslist=sserv.viewSong();

		//Adding the songs in the model
		model.addAttribute("songslist", songslist);

		//sending createplaylist
		return "createplaylist";
	}

	@PostMapping("/addplaylist")
	public String addPlayList(@ModelAttribute Playlist playlist)
	{
		//System.out.println(playlist);
		//adding playlist
		pserv.addPlaylist(playlist);
		//update song table
		List<Song> songsList=playlist.getSong();
		for(Song song: songsList)
		{
			song.getPlaylist().add(playlist);
			sserv.updateSong(song);
		}
		
		
		return "playlistsuccess";
	}

	
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model)
	{
		List<Playlist> plist=pserv.fetchPlaylists();
		//System.out.println(plist);
		model.addAttribute("plist",plist);
		return "viewplaylist";
	}

}

