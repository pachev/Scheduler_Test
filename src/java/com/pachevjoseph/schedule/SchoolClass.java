/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pachevjoseph.schedule;

/**
 *
 * @author drawven
 */
public class SchoolClass {
private String title;
private int startTime;
private int endTime;
private int day;
private int credit;

    public SchoolClass() {
      
    }



public SchoolClass(String title, int startTime, int endTime, int day, int credit)
{
this.title = title;
this.startTime = startTime;
this.endTime = endTime;
this.day = day;
this.credit = credit;
}

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the startTime
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return title+","+startTime+","+endTime+","+day+","+credit;
    }



    
}
