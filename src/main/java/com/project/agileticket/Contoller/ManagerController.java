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
import com.project.agileticket.Service.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	ITicketService ticketService;
	@Autowired
	IUserService userService;
	@GetMapping("/managerpage")
	public String managerDashboardPage(Model model, HttpServletRequest request)
	{
		
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		User user = (User)request.getSession().getAttribute("user");
		userService.validateUserByCredentials(user.getName().toLowerCase(), user.getPassword(), user.getEmploymentType().toLowerCase());
		
		List<Ticket> tickets = new ArrayList<>();
		
		tickets = ticketService.getTicketsCreatedBytManager(user.getId());
		Comment comment = new Comment();
		
		model.addAttribute("tickets", tickets);
		model.addAttribute("comment", comment);
		return "managerpage";
	}
}
