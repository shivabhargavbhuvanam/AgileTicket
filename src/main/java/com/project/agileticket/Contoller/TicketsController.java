package com.project.agileticket.Contoller;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.agileticket.Pojo.Comment;
import com.project.agileticket.Pojo.Ticket;
import com.project.agileticket.Pojo.User;
import com.project.agileticket.Service.ICommentService;
import com.project.agileticket.Service.ITicketService;
import com.project.agileticket.Service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ticket")
public class TicketsController {
	@Autowired
	private ITicketService ticketService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ICommentService commentService;

	@GetMapping("/{id}")
	public String getTicket(Model model, @PathVariable String id, HttpServletRequest request)
	{
		User user = (User)request.getSession().getAttribute("user");
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(user == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		Ticket ticket = new Ticket();
		ticket = ticketService.getTicketById(id);
		
		if(!ticket.getManager().getName().equals(user.getName()))
		{
			return "redirect:/user/logout";
		}
		
		model.addAttribute("ticket", ticket);

		Comment comment = new Comment();
		model.addAttribute("comment", comment);
		
		List<Comment> comments = commentService.getCommentsByTicketId(ticket.getTicketNumber());
		model.addAttribute("comments", comments);
		
		return "ticket";
	}
	
	@PostMapping("/{id}")
	public String managerEditTicketById(Model model, @PathVariable String id, @ModelAttribute Ticket updatingticket, HttpServletRequest request)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		System.out.print("\nReached edit post method with ticketid " + id);
		
		System.out.print("\nupdating ticket comments: " + updatingticket.getComments());
		Ticket ticket = new Ticket();
		ticket = ticketService.getTicketById(id);
		if(!ticket.getManager().getName().equals(user.getName()))
		{
			return "redirect:/user/logout";
		}
		
		if(ticket.getManager() == null)
		{
			return accessDenialUtilMethod(ticket, user, model);
		}
		
		if(ticket.getManager().getId() != user.getId())
		{
			return accessDenialUtilMethod(ticket, user, model);
		}
		
		Field[] fields = ticket.getClass().getDeclaredFields();
		updatingticket.setTicketNumber(ticket.getTicketNumber());
		
		for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(updatingticket);
                System.out.println("Field Name: " + field.getName() + ", Value: " + value);
                if (value != null) {
                    field.set(ticket, value);
                }
            } catch (IllegalAccessException e) {
                System.err.println("Access denied: " + e.getMessage());
            }
        }
		if(updatingticket.getAssignee()!=null)
		{
			ticket.setTicketUnder(userService.getUserbyUsername(ticket.getAssignee()));
		}
		
		ticket.setModifiedTime(LocalDateTime.now());
		
		ticketService.editTicketByManager(ticket);
		
		Comment comment = new Comment();
		model.addAttribute("comment", comment);
		
		List<Comment> comments = commentService.getCommentsByTicketId(ticket.getTicketNumber());
		model.addAttribute("comments", comments);
		
		return "ticket";
	}
	
	
	@GetMapping("/createticket")
	public String ticketCreationForm(Model model, HttpServletRequest request)
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
		if(!user.getEmploymentType().toLowerCase().equals("manager"))
		{
			return "redirect:/user/logout";
		}
		model.addAttribute("ticket", new Ticket());
		return "ticketcreation";
	}
	
	@PostMapping("/createticket")
	public String createTicket(Model model, @ModelAttribute Ticket ticket, HttpServletRequest request)
	{
		try {
			
			if(!request.isRequestedSessionIdValid())
			{
				return "loginfailure";
			}
			if(request.getSession().getAttribute("user") == null)
			{
				return "redirect:/user/welcomelogin";
			}
			
		User user = new User();
		user = (User)request.getSession().getAttribute("user");
		
		if(!user.getEmploymentType().toLowerCase().equals("manager"))
		{
			return "redirect:/user/logout";
		}

		System.out.println(user.getName());
		
		ticket.setTicketUnder(userService.getUserbyUsername(ticket.getAssignee()));
		System.out.println(ticket.getTicketUnder());
		ticket.setManager(user);
		ticket.setCreationTime(LocalDateTime.now());
		ticket.setModifiedTime(LocalDateTime.now());
		System.out.println(ticket.getName());
		Ticket localTicket = ticketService.createTicket(ticket);
		if(localTicket == null)
		{
			return "creationfailure";
		}
		return "creationsuccess";
		}
		catch(Exception e)
		{
			return "creationfailure";
		}
	}
	
	private String accessDenialUtilMethod(Ticket ticket, User currentUser, Model model)
	{
		
		if(ticket.getManager() != null)
		{
			System.out.println("manager and creator are not same "+ticket.getManager().getName()+" "+currentUser.getName());
		}
		model.addAttribute("ticket",ticket);
		return "accessdenied";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTicketdisplay(Model model, HttpServletRequest request, @ModelAttribute Ticket ticket, @PathVariable String id)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		User user = (User)request.getSession().getAttribute("user");
		if( user == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		
		if(!user.getEmploymentType().toLowerCase().equals("manager"))
		{
			return "redirect:/user/logout";
		}

		
		System.out.println("Get api "+ ticket);
		System.out.println(id);
		if(ticket == null)
		{
			ticket = ticketService.getTicketById(id);
		}
		if (ticket == null) {
	        
			System.out.print(ticket);
	        return "redirect:/admin/adminpage";
	    }
		return "ticketdeletionfailure";
	}
	@PostMapping("/delete/{id}")
	public String deleteTicket(Model model, HttpServletRequest request, @PathVariable String id)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		if(!user.getEmploymentType().toLowerCase().equals("manager"))
		{
			return "redirect:/user/logout";
		}
		
		Ticket ticket=new Ticket();
		System.out.println(id);
		System.out.println("Post api "+ ticket);
		try {
			ticket = ticketService.getTicketById(id);
			System.out.println("Post api "+ ticket.getName());
			User managerDetails = (User)request.getSession().getAttribute("user");
			System.out.println(ticket.getDescription());
			
			System.out.println(managerDetails.getName());
			if(ticket.getManager().getId() != managerDetails.getId())
			{
				return accessDenialUtilMethod(ticket, managerDetails, model);
			}
			System.out.print("\ndeletion started-Controller");
			Ticket localTicket = ticketService.deleteTicket(ticket);
			if(localTicket==null)
			{
				model.addAttribute("ticket",ticket);
				return "ticketdeletionfailure";
			}
			return "redirect:/manager/managerpage";
		}
		catch(Exception e)
		{
			model.addAttribute("ticket",ticket);
			return "ticketdeletionfailure";
		}
	}
	
	@GetMapping("/developer/{id}")
	public String getDeveloperTicket(Model model, @PathVariable String id, HttpServletRequest request)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)
		{
			return "redirect:/user/welcomelogin";
		}
		if(!user.getEmploymentType().toLowerCase().equals("developer"))
		{
			return "redirect:/user/logout";
		}
		
		Ticket ticket = new Ticket();
		ticket = ticketService.getTicketById(id);
		model.addAttribute("ticket", ticket);
		model.addAttribute("manager", ticket.getManager());
		Comment comment = new Comment();
		model.addAttribute("comment",comment);
		
		List<Comment> comments = commentService.getCommentsByTicketId(Integer.parseInt(id.split("-")[1]));
		model.addAttribute("comments",comments);
		return "developerticket";
	}
	
	@PostMapping("/developer/{id}")
	public String EditTicketById(Model model, @PathVariable String id, @Valid @ModelAttribute Ticket updatingticket, HttpServletRequest request)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)
		{
			return "redirect:/user/welcomelogin";
		}
		if(!user.getEmploymentType().toLowerCase().equals("developer"))
		{
			return "redirect:/user/logout";
		}
		
		System.out.print("\nReached edit post method with ticketid " + id);
		Ticket ticket = new Ticket();
		ticket = ticketService.getTicketById(id);
		
		model.addAttribute("ticket", ticket);
		Comment comment = new Comment();
		List<Comment> comments = commentService.getCommentsByTicketId(Integer.parseInt(id.split("-")[1]));
		model.addAttribute("comment",comment);
		model.addAttribute("comments",comments);
		model.addAttribute("manager", ticket.getManager());
		
		return "developerticket";
	}
	
}
