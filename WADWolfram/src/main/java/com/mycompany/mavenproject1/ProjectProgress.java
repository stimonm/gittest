package com.mycompany.mavenproject1;

public class ProjectProgress {

    private String title;

    private int percentage;

    protected ProjectProgress(){} 

    public ProjectProgress(String title, int percentage){
      this.title=title;
      this.percentage=percentage;
    }

    public String getTitle(){
      return title;
    }

    public void setTitle(String title){
      this.title=title;
    }

    public int getPercentage(){
      return percentage;
    }

    public void setPercentage(int percentage){
      this.percentage=percentage;
    }

}