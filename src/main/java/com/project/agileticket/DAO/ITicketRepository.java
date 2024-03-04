package com.project.agileticket.DAO;

import java.util.List;

import com.project.agileticket.Pojo.Ticket;

public interface ITicketRepository{
	public List<Ticket> managerRelatedTickets(int userid);
	public List<Ticket> userRelatedTickets(int userid);
	public Ticket ticketByNumber(int ticketNumber);
	public Ticket getTicketById(String ticketid);
	public void save(Ticket ticket);
	public Ticket merge(Ticket ticket);
	public Ticket delete(Ticket ticket);
}
