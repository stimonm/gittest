package com.mycompany.mavenproject1;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

public class User {

// @ManyToMany
	private List<UserProject> colaborateProjects;
// @ManyToMany
	private List<UserProject> otherProjects;
 //@OneToMany(mappedBy="user")
	private List<UserMovements> donations;
 //@OneToOne(cascade=CascadeType.ALL)
	UserPersonalData user;
 /*@ElementCollection(fetch = FetchType.EAGER)
 private List<String> roles;*/
	
 protected User(){}	

	/*public User(List<Project> colaborateProjects, List<Project> otherProjects,
			List<Donation> donations, UserPersonalData user, List<String> roles) {
		this.colaborateProjects = colaborateProjects;
		this.otherProjects = otherProjects;
		this.donations=donations;
		this.user = user;
  this.roles=roles;
	}*/

    /*public User(UserPersonalData user){
		this.colaborateProjects = new ArrayList<>();
		this.otherProjects = new ArrayList<>();
		this.donations = new ArrayList<>();
		this.user = user;
  this.roles = new ArrayList<>();
	}*/

	public List<UserProject> getColaborateProjects() {
		return colaborateProjects;
	}

	public User(List<UserProject> colaborateProjects, List<UserProject> otherProjects, List<UserMovements> donations,
			UserPersonalData user) {
		super();
		this.colaborateProjects = colaborateProjects;
		this.otherProjects = otherProjects;
		this.donations = donations;
		this.user = user;
	}

	public void setColaborateProjects(List<UserProject> colaborateProjects) {
		this.colaborateProjects = colaborateProjects;
	}

 /*public void addColaborateProjects(Project p){
   colaborateProjects.add(p);
 }*/

	public List<UserProject> getOtherProjects() {
		return otherProjects;
	}

	public void setOtherProjects(List<UserProject> otherProjects) {
		this.otherProjects = otherProjects;
	}

/* public void addOtherProject(Project p){
   otherProjects.add(p);
 }*/

	public List<UserMovements> getDonations(){
		return donations;
	}

	public void setDonation(List<UserMovements> donations){
		this.donations=donations;
	}

 /*public void addDonation(Donation d){
   donations.add(d);
 }*/

	public UserPersonalData getUser() {
		return user;
	}

	public void setUser(UserPersonalData user) {
		this.user = user;
	}	

   /* public List<String> getRoles(){ 
   return roles;
 }
 public void setRoles(List<String> roles){
   this.roles=roles;
 }
 public void addRole(String r){
   roles.add(r);
 }*/
}