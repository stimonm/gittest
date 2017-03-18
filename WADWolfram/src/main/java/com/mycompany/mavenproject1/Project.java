package com.mycompany.mavenproject1;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String shortDescription;
    private String description;
    private double totalBudget;
    private double parcialBudget;
    private double restBudget;
    private double time;
    private boolean opened;
    private Date releaseDate;
    private int startYear;
    private String image;
    @OneToMany(mappedBy="project")
    private List<Donation> donations;

    protected Project() {
    }
    
    public Project(String title, String shortDescription, String description, double totalBudget, double parcialBudget,
			double time, boolean opened, Date releaseDate, int startYear, String image) {
		this.title = title;
		this.shortDescription = shortDescription;
		this.description = description;
		this.totalBudget = totalBudget;
		this.parcialBudget = parcialBudget;
		this.restBudget = totalBudget-parcialBudget;
		this.time = time;
		this.opened = opened;
		this.releaseDate = releaseDate;
		this.startYear = startYear;
		this.image = image;
  this.donations=new ArrayList<>();
	}

	/*public Project(long id,String title, String shortDescription, String description, double totalBudget, double parcialBudget, double time, boolean opened, Date releaseDate, int startYear, String image) {
        super();
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.totalBudget = totalBudget;
        this.parcialBudget = parcialBudget;
        this.restBudget = totalBudget - parcialBudget;
        this.time = time;
        this.opened = opened;
        this.releaseDate = releaseDate;
        this.startYear = startYear;
        this.image = image;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setRestBudget(double restBudget) {
        this.restBudget = restBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
        this.setRestBudget(this.totalBudget - this.parcialBudget);
    }

    public double getParcialBudget() {
        return parcialBudget;
    }

    public void setParcialBudget(double parcialBudget) {
        this.parcialBudget = parcialBudget;
    }

    public double getRestBudget() {
        return restBudget;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean getOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public List<Donation> getDonations(){
      return donations;
    }

    public void setDonations(List<Donation> donations){
      this.donations=donations;
    }
    public void addDonation(Donation d){
      donations.add(d);
      setParcialBudget(parcialBudget+d.getMoney());
     }

	/*@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Project [id=" + id + 
				", title="+title
				+", shortDescription" + shortDescription
				+", description" + description
				+", totalBudget" + totalBudget
				+", parcialBudget" + parcialBudget
				+", restBudget" + restBudget
				+", time" + time
				+", opened" + opened
				+", releaseDate" + releaseDate
				+", startYear" + startYear
				+", image" + image;
	}*/

  public int calculateProgressPercentage(){
    return (int)(parcialBudget*100/totalBudget);
  }    
}
