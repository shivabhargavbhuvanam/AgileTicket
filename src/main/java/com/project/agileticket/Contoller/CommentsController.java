package com.project.agileticket.Contoller;

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
import com.project.agileticket.Pojo.CommentsWrapper;
import com.project.agileticket.Pojo.Ticket;
import com.project.agileticket.Pojo.User;
import com.project.agileticket.Service.ICommentService;
import com.project.agileticket.Service.ITicketService;
import com.project.agileticket.Service.IUserService;


import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comment")
public class CommentsController {
	@Autowired
	ITicketService ticketService;
	@Autowired
	ICommentService commentService;
	@Autowired
	IUserService userService;
	
	
	@GetMapping("/ticket/{id}")
	public String getAllCommentsByTicketId(@PathVariable String id, HttpServletRequest request, Model model)
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
		Ticket ticket = ticketService.getTicketById(id);
		
		if(!ticket.getManager().getName().equals(user.getName()))
		{
			return "redirect:/user/logout";
		}
		
		
		int ticket_ticketNumber = Integer.parseInt((id.split("-"))[1]) ;
		
		
		
		List<Comment> comments = commentService.getCommentsByTicketId(ticket_ticketNumber);
		
		model.addAttribute("comments", comments);
		model.addAttribute("ticket", comments.get(0).getTicket());
		CommentsWrapper commentsWrapper = new CommentsWrapper();
		commentsWrapper.setComments(comments);

	    model.addAttribute("commentsWrapper", commentsWrapper);
		
		return "comment";
	}
	
	@PostMapping("/ticket/{id}/add")
	public String addCommentByUser(@PathVariable String id, HttpServletRequest request, @ModelAttribute Comment comment, Model model)
	{
		
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		}
			User user = (User) request.getSession().getAttribute("user");

			comment.setTicket(ticketService.getTicketById(id));
			comment.setUser(user);
			comment.setCreationTime(LocalDateTime.now());
			comment.setModificationTime(LocalDateTime.now());
			
			System.out.println("comment text: " + comment.getText());
			System.out.println("comment text: " + comment.getCreationTime());
			System.out.println("comment text: " + comment.getUser().getName());
			
			Comment localComment = commentService.addCommentByTicketId(comment);
			
			if(localComment!=null)
			{
				System.out.println(localComment.getCommentid());
			}
			
			model.addAttribute("ticket",comment.getTicket());
		
			
		if(user.getEmploymentType().toLowerCase().equals("manager"))
		{
			return "redirect:/ticket/"+id;
		}
		return "redirect:/ticket/developer/"+id;
	}
	
	@GetMapping("/ticket/{id}/edit")
	public String showEditCommentsForm(@PathVariable String id, Model model, HttpServletRequest request) {
		
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
	    List<Comment> comments = commentService.getCommentsByTicketId(Integer.parseInt(id.split("-")[1]));
	    CommentsWrapper commentsWrapper = new CommentsWrapper();
	    commentsWrapper.setComments(comments);

	    model.addAttribute("commentsWrapper", commentsWrapper);
	    model.addAttribute("ticketId", id);

	    return "comment";
	}
	
	@PostMapping("/ticket/{id}/edit")
	public String editCommentsInATicket(@ModelAttribute CommentsWrapper commentsWrapper, Model model, @PathVariable String id, HttpServletRequest request)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		List<Comment> oldComments = commentService.getCommentsByTicketId(Integer.parseInt((id.split("-"))[1]));
		
		for (int i = 0; i < commentsWrapper.getComments().size(); i++) {
			Comment updatedComment = commentsWrapper.getComments().get(i);
			if (!updatedComment.getText().equals(oldComments.get(i).getText())) {
	            oldComments.get(i).setModificationTime(LocalDateTime.now());
	            oldComments.get(i).setText(updatedComment.getText());
	            commentService.editCommentInTicket(oldComments.get(i));
	        }
	    }
		
		return "redirect:/ticket/"+id;
	}
}
