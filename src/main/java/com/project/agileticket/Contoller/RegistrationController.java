package com.project.agileticket.Contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.agileticket.Pojo.User;
import com.project.agileticket.Service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@Controller
@Validated
public class RegistrationController {
	@Autowired
	IUserService userService;
	
	@GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration"; 
    }
	
	@PostMapping("/register")
    public String registerUser(Model model,@Valid @ModelAttribute User user, HttpServletRequest request) 
	{
		
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		User currentUser = (User)request.getSession().getAttribute("user");
		
		if(currentUser == null)
		{
			return "redirect:/user/welcomelogin";
		}
		if(!currentUser.getEmploymentType().toLowerCase().equals("admin"))
		{
			return "redirect:/user/logout";
		}
        
		try {
			
		List<User> allusers= userService.getAllUsers();
		
		for(User eachuser: allusers)
		{
			if(user.getName().equals(eachuser.getName()))
			{
				return "registrationfailure";
			}
		}
		
		User newUser = userService.registerUser(user);
		if(newUser == null)
		{
			return "registrationfailure";
		}
		
		model.addAttribute("user",newUser);

        return "registrationsuccess"; 
		}
		catch(Exception e)
		{
			return "registrationfailure";
		}
    }
}
