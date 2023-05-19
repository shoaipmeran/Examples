package com.digitalbooks.book.dto;


public class UserDto {

    private long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String messagess;
    
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMessagess() {
		return messagess;
	}
	public void setMessagess(String messagess) {
		this.messagess = messagess;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
