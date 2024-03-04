package com.project.agileticket.Service;

import java.util.List;

import com.project.agileticket.Pojo.Comment;

public interface ICommentService {
	public List<Comment> getCommentsByTicketId(int ticket_ticketNumber);
	public Comment addCommentByTicketId(Comment comment);
	public void editCommentInTicket(Comment comment);

}
