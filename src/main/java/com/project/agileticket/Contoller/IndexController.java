package com.project.agileticket.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@Autowired
	UserController userController;
	
	@GetMapping("")
	public String indexForm(Model model)
	{
		return "redirect:/user/welcomelogin";
	}
}
