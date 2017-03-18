package com.mycompany.mavenproject1;

import java.util.Date;
import java.util.Date;

public class UserMovements {
	private String projectTitle;
	private double money;
	private Date date;

 protected UserMovements(){}	
	public UserMovements(String projectTitle, double money, Date date) {
		super();
		this.projectTitle = projectTitle;
		this.money = money;
		this.date = date;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
