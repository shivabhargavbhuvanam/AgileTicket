package com.project.agileticket.DAO;

import java.util.List;

import com.project.agileticket.Pojo.Comment;

public interface ICommentRepository {
	public List<Comment> getCommentsByTicketId(int ticket_ticketNumber);
	public Comment addCommentByTicket(Comment comment);
	public void editCommentInTicket(Comment comment);

}
