package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entities.Users;
import com.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController
{
	@Autowired
	UsersService userv;

	@PostMapping("/register")
	public String addUser(
			@ModelAttribute Users user) 
	{

		boolean userstatus = userv.emailExists(user.getEmail());
		if(userstatus == false)
		{
			userv.addUser(user);
			System.out.println("User is added");
			return "registersuccess";
		}

		else
		{
			System.out.println("User is already exist");
			return "registerfail";
		}



	}

	@PostMapping("/login")
	public String validateUser(@RequestParam String email, @RequestParam String password,
	                           HttpSession session, Model model) {
	    if (!userv.emailExists(email)) {
	        return "loginfail";
	    }

	    if (userv.validateUser(email, password)) {
	        session.setAttribute("email", email);
	        String username = userv.getUserName(email); 
	        model.addAttribute("username", username);
	        if (userv.getRole(email).equals("admin")) {
	            return "adminhome";
	        } else {
	            return "customerhome";
	        }
	    } else {
	        return "loginfail";
	    }
	}

	
	
	@GetMapping("/exploresongs")
	public String exploresongs(HttpSession session)
	{
		String email=(String) session.getAttribute("email");
		Users user=userv.getUser(email);
		
		boolean userStatus=user.isPremium();
		if(userStatus==true)
		{
			return "premiumcustomer";
		}
		else
		{
			return "payment";
		}
	}
	
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidating session
        session.invalidate();
        return "index";
    
    }
	
	@GetMapping("/forgot-password")
	public String showForgotPasswordForm(Model model) {
	    // Add a message attribute to the model
	    model.addAttribute("message", "");
	    return "forgotpassword"; // Return the name of your HTML file (without .html extension)
	}

	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam String email, @RequestParam String password, Model model) {
	    Users user = userv.getUser(email);

	    if (user == null) {
	        // If user is not found, set message and return to forgot password page
	        model.addAttribute("message", "User not found");
	        return "forgotpassword";
	    } else {
	        // Update user's password
	        user.setPassword(password);
	        // Save the updated user
	        userv.updateUser(user);
	        // Set message and redirect to login page
	        model.addAttribute("message", "Password updated successfully");
	        return "forgotpassword"; // Redirect to the login page
	    }
	
	}



}



