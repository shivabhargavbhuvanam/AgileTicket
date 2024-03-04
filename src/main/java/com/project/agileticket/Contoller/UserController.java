package com.project.agileticket.Contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.agileticket.Pojo.Ticket;
import com.project.agileticket.Pojo.User;
import com.project.agileticket.Service.ITicketService;
import com.project.agileticket.Service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private IUserService userService;
	@Autowired
	private ITicketService ticketService;
	
	@GetMapping("/edit/{userid}")
	public String getUserInfo(Model model, @PathVariable int userid, HttpServletRequest request)
	{
		
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		User user = userService.findUserById(userid);
		if (user == null) {
	       
			System.out.print(user);
	        return "editfailure";
	    }
		model.addAttribute("user",user);
		return "edituser";
	}
	
	@PostMapping("/edit/{userid}")
	public String editUser(Model model, @PathVariable int userid  ,@Valid @ModelAttribute User updatingUser, HttpServletRequest request)
	{	
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		System.out.print("\nReached edit post method with userid " + userid);
		User user = new User();
		user = userService.findUserById(userid);
		user.setName(updatingUser.getName());
		user.setEmploymentType(updatingUser.getEmploymentType());
		User updatedUser = userService.updateUserByUserId(updatingUser.getId(), user);
		if(updatedUser == null)
		{
			return "editfailure";
		}
		return "redirect:/admin/adminlogin";
	}
	
	@GetMapping("delete/{userid}")
	public String deleteUserPage(Model model, @PathVariable int userid, HttpServletRequest request)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		}
		
		User user = userService.findUserById(userid);
		if (user == null) {
	        
			System.out.print(user);
	        return "redirect:/admin/adminpage";
	    }
		return "deletionfailure";
	}
	
	@PostMapping("/delete/{userid}")
	public String deleteUser(Model model, @PathVariable int userid, HttpServletRequest request)
	{
		if(!request.isRequestedSessionIdValid())
		{
			return "loginfailure";
		}
		if(request.getSession().getAttribute("user") == null)
		{
			return "redirect:/user/welcomelogin";
		} 
		
		User user = userService.findUserById(userid);
		List<Ticket> tickets = ticketService.getTicketsUnderUser(user.getId());
		if(tickets != null && !tickets.isEmpty() && tickets.getFirst() != null && tickets.getFirst().getId()!=null)
		{
			System.out.println(tickets.getFirst().getName());
			model.addAttribute("user", user);
			return "deletionfailure";
		}
		
		System.out.println("reached deleteUser");
		User checkDeletion = userService.deleteUserByUser(user);
		if(checkDeletion == null)
		{
			model.addAttribute("user", user);
			return "deletionfailure";
		}
		return "redirect:/admin/adminlogin";
	}
	
	@GetMapping("/welcomelogin")
	public String welcomeLoginForm(Model model)
	{
		User user = new User();
		model.addAttribute(user);
		return "welcomelogin";
	}
	
	@PostMapping("/welcomelogin")
	public String welcomeLogin(Model model,@Valid @ModelAttribute("user") User user, HttpServletRequest request)
	{
		System.out.print(user.getName()+" "+user.getPassword()+"\n");
		User validUser = userService.validateUserByCredentials(user.getName().toLowerCase(), user.getPassword(), user.getEmploymentType().toLowerCase());
		if(validUser==null)
		{
			System.out.print(validUser+"\n");
			return "loginfailure";
		}
		request.getSession().setAttribute("user", validUser);
		if(validUser.getEmploymentType().toLowerCase().equals("admin"))
		{
			System.out.print("Error not reached yet");
			return "redirect:/admin/adminlogin";
		}
		if(validUser.getEmploymentType().toLowerCase().equals("manager"))
		{
			System.out.println("\nsuccessfully saved manager details and continue with implementation\n");
			return "redirect:/manager/managerpage";
		}
		if(validUser.getEmploymentType().toLowerCase().equals("developer"))
		{
			System.out.println("\nsuccessfully saved developer details and continue with implementation\n");
			return "redirect:/developer/developerpage";
		}
		model.addAttribute(validUser);
		return "welcomelogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request)
	{
		request.getSession().invalidate();
		  return "redirect:/user/welcomelogin";
	}
	
	
}
