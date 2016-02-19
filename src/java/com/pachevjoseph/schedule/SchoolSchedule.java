/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pachevjoseph.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author drawven
 */
public class SchoolSchedule {
    
    private ArrayList<SchoolClass> classes = new ArrayList<>();

    public List getClasses() {
        return classes;
    }

    public ArrayList<SchoolClass> getAClasses() {
        return classes;
    }
    
    
    
    public void addClass(SchoolClass schoolClass) {
        classes.add(schoolClass);
    }

    
    
}
