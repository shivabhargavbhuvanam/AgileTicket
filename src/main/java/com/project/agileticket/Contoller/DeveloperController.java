package com.project.agileticket.Contoller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.agileticket.Pojo.Comment;
import com.project.agileticket.Pojo.Ticket;
import com.project.agileticket.Pojo.User;
import com.project.agileticket.Service.ITicketService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/developer")
public class DeveloperController {
	@Autowired
	ITicketService ticketService;
	
	@GetMapping("/developerpage")
	public String developerDashboard(HttpServletRequest request, Model model )
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		User user = (User) request.getSession().getAttribute("user");
		if(user == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		if(!user.getEmploymentType().toLowerCase().equals("developer"))
		{
			return "redirect:/user/logout";
		}
		
		
		List<Ticket> tickets = new ArrayList<>();
		
		tickets = ticketService.getTicketsUnderUser(user.getId());
		
		model.addAttribute("tickets", tickets);
		Comment comment = new Comment();
		model.addAttribute("comment", comment);
		return  "developerpage";
	}
}
