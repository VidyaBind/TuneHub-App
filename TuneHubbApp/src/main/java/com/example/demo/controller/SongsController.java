package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Songs;
import com.example.demo.services.SongsService;




@Controller
public class SongsController {
	@Autowired
	SongsService songserv;

	@PostMapping("/addsongs")
	public String addSongs(@ModelAttribute Songs song){
		
		boolean status = songserv.songExists(song.getName());
		if(status==false)
		{
			songserv.addSongs(song);
			System.out.println("Song is added Successfully.");
		}
		else {
			System.out.println("Song '"+song.getName()+"' is already Exist.");
	}
	return "adminHome";
}

	@GetMapping("/viewSong")
	public String viewSong(Model model) {
		List<Songs> songslist=songserv.fetchAllSongs();
//		System.out.println(songsList);
		model.addAttribute("songslist", songslist);
		return "displaysongs";
	}
	
	@GetMapping("/viewSongC")
	public String viewCustomerSongs(Model model) {
		List<Songs> songslist=songserv.fetchAllSongs();
//		System.out.println(songsList);
		model.addAttribute("songslist", songslist);
		return "displaysongsC";
	}

//	@GetMapping("/exploreSongs")
//	public String viewCustomerSongs(Model model) {
		
		
	//		List<Songs> songslist = songserv.fetchAllSongs();
	//		model.addAttribute("songslist", songslist);
	//		return "displaysongs";
	//	}
		

	}



















