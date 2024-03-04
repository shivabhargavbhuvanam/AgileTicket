package com.project.agileticket.Contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.agileticket.Pojo.User;
import com.project.agileticket.Service.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	IUserService userService;
	
	@GetMapping("/adminlogin")
	public String adminPage(Model model, HttpServletRequest request)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		User user = (User)request.getSession().getAttribute("user");
		userService.validateUserByCredentials(user.getName().toLowerCase(), user.getPassword(), user.getEmploymentType().toLowerCase());
		
		System.out.print("adminPage is reached");
		List<User> usersList = userService.getAllUsers();
		model.addAttribute("usersList", usersList);
		return "adminpage";
	}
}
