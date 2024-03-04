package com.project.agileticket.Service;

import java.util.List;

import com.project.agileticket.Pojo.Ticket;

public interface ITicketService {
	public Ticket getTicketById(String ticketId);
	public Ticket deleteTicket(Ticket ticket);
	public void updateTicket(Ticket ticket);
	public Ticket createTicket(Ticket ticket);
	public List<Ticket> getTicketsCreatedBytManager(int userid);
	public List<Ticket> getTicketsUnderUser(int userid);
	public Ticket editTicketByManager(Ticket ticket);
	public Ticket editTicketByDeveloper(Ticket tickets);
}
