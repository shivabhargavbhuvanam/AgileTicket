package com.project.agileticket.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.agileticket.DAO.ICommentRepository;
import com.project.agileticket.Pojo.Comment;

@Service
public class CommentService implements ICommentService {
	@Autowired
	ICommentRepository commentRepository;
	
	public List<Comment> getCommentsByTicketId(int ticket_ticketNumber)
	{
		try {
			return commentRepository.getCommentsByTicketId(ticket_ticketNumber);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Comment addCommentByTicketId(Comment comment)
	{
		try {
			return commentRepository.addCommentByTicket(comment);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void editCommentInTicket(Comment comment)
	{
		try {
			commentRepository.editCommentInTicket(comment);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
