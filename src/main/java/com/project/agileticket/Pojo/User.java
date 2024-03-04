package com.project.agileticket.Pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", unique = true, nullable = false)
	private int userId;
	@NotEmpty(message="Cannot be blank")
	@Size(min = 6, max=45, message = "Username should have 6-45 characters")
	@Column(name = "name", unique = true)
	private String name;
	@Column(name = "employmentType")
	private String employmentType;
	@Size(min = 6, max = 15, message = "Password should be between 6 and 15 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,15}$", message = "Password should contain at least one digit, one lowercase letter, and one uppercase letter, and no whitespace")
    @Column(name = "password")
	private String password;
	
	public String getName()
	{
		return this.name;
	}
	public String getEmploymentType()
	{
		return this.employmentType;
	}
	public String getPassword()
	{
		return this.password;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setEmploymentType(String employmentType)
	{
		this.employmentType = employmentType;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public int getId()
	{
		return this.userId;
	}
	public void setId(int userId)
	{
		this.userId = userId;
	}
}
