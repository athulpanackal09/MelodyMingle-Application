package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.entities.Song;
import com.demo.services.SongService;

@Controller
public class SongsController {

	@Autowired
	SongService songser;

	@PostMapping("/addsongs")
	public String addSongs(Song song) {

		boolean songstatus=songser.nameExists(song.getName());
		if(songstatus==false)
		{
			songser.addSongs(song);
			return "songaddedsuccess";
		}
		else
		{
			return "songaddedfail";
		}
	}

	@GetMapping("/map-viewsongs")
	public String viewSong(Model model)
	{
		List<Song> songlist=songser.viewSong();

		model.addAttribute("Song", songlist);

		return "viewsong";
	}

	
	/*
	@GetMapping("/viewsongs")
	public String viewCustomerSongs (Model model) {

		boolean primeCustomerStatus=false;

		if(primeCustomerStatus == true)
		{
			List<Song> songslist=songser.viewSong();

			model.addAttribute("songslist", songslist);

			return "viewsong";
		}
		else
		{
			return "makepayment";
		}
	}
	
	*/



}

