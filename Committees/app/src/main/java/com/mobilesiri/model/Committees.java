package com.mobilesiri.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by mhasa on 12/1/2016.
 */
public class Committees implements Serializable {

    private String id="";
    private String name = "", longtitle="";
    private String introduced_on = "";
    private String parent = "";
    private String chamber="";
    private String office = "";
    private String fname="", lname="";
    private Boolean active = false;
    private String contact="";
    private String version="";
    private String pdf="";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public void setid(String input){
        this.id = input.toUpperCase();
    }
    public String getID(){return id;}

    public void setname(String input){
        this.name = input;
    }
    public String getname(){
        return name;
    }

    public void setparent (String input){
        this.parent = input;
    }
    public String getparent(){return parent;}

    public void setoffice (String input){
        this.office = input;
    }
    public String getoffice(){return office;}

    public void setcontact (String input){
        this.contact = input;
    }
    public String getcontact(){return contact;}

    public void setChamber(String chamber){
        this.chamber = chamber.substring(0, 1).toUpperCase() + chamber.substring(1);
    }
    public String getChamber(){ return chamber;}

}

