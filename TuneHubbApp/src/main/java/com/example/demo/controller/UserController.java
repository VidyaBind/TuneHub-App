package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;



import com.example.demo.entities.Users;
import com.example.demo.services.SongsService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController 
{
	@Autowired
	UsersService userv;
	
	@Autowired
	SongsService songserv;

	@PostMapping("/register")
	public String addUser(@ModelAttribute Users user) {
		
		boolean userstatus = userv.emailExists(user.getEmail());
		if(userstatus == false) {
			userv.addUser(user);
			System.out.println("User added Successfully.");
		} else {
			
			System.out.println("E-mail already exist");
		}

		return "home";

	}
	
	@PostMapping("/validate")
	public String validateUser(@RequestParam String email, @RequestParam String password, HttpSession session,
			Model model) {

		if (userv.validateUser(email, password) == true) {
			String role = userv.getRole(email);

			session.setAttribute("email", email);
			if (role.equals("admin")) {
				return "adminhome";
			} else {
				Users user = userv.getUser(email);
				boolean userStatus = user.isPremium();
				model.addAttribute("isPremium", userStatus);
				return "customerhome";
			}
		} else {
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logut(HttpSession session) {
		session.invalidate();
		return "login";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam String email, @RequestParam String newPassword, Model model) {

		boolean emailExists = userv.emailExists(email);

		if (emailExists) {
			Users user = userv.getUser(email);
			user.setPassword(newPassword);
			userv.updateUser(user);
			return "login";
		}
		return "resetPassword";
	}
}
	
	
















