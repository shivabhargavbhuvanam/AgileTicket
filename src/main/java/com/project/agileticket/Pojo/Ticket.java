package com.project.agileticket.Pojo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticketNumber", unique = true, nullable = false)
	private int ticketNumber;
	

	@Column(name = "ticket_id", unique = true)
	private String id;
	private String name;// name of ticket
	private String description;
	private String type;
	private String assignee;// name of the person under whose name the current ticket is assigned to
	private String status;
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> comments = new ArrayList<>();
	public List<Comment> getComments()
	{
		return this.comments;
	}
	public void setComments(List<Comment> comments)
	{
		this.comments=comments;
	}
	public void addComment(Comment comment)
	{
		this.comments.add(comment);
	}
	
	@ManyToOne
	@JoinColumn(name = "manager_userid", referencedColumnName = "userid")
	private User manager;
	public User getManager()
	{
		return this.manager;
	}
	public void setManager(User manager)
	{
		this.manager=manager;
	}
	
	@ManyToOne
	@JoinColumn(name = "ticketUnder_userid", referencedColumnName = "userid")
	private User ticketUnder;
	public User getTicketUnder()
	{
		return this.ticketUnder;
	}
	public void setTicketUnder(User ticketUnder)
	{
		this.ticketUnder=ticketUnder;
	}
	
	 @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime creationTime;
	 @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime modifiedTime;
	
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public int getTicketNumber()
	{
		return this.ticketNumber;
	}
	public void setTicketNumber(int ticketNumber)
	{
		this.ticketNumber=ticketNumber;
	}
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getDescription()
	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}
	public String getAssignee()
	{
		return this.assignee;
	}
	public void setAssignee(String assignee)
	{
		this.assignee=assignee;
	}
	public String getStatus()
	{
		return this.status;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type=type;
	}
	public LocalDateTime getCreationTime()
	{
		return this.creationTime;
	}
	public void setCreationTime(LocalDateTime creationTime)
	{
		this.creationTime=creationTime;
	}
	public LocalDateTime getModifiedTime()
	{
		return this.modifiedTime;
	}
	public void setModifiedTime(LocalDateTime modifiedTime)
	{
		this.modifiedTime=modifiedTime;
	}
}
