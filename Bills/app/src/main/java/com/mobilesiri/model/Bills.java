package com.mobilesiri.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mhasa on 12/1/2016.
 */

public class Bills implements Serializable {

    private String id="";
    private String shorttitle = "", longtitle="";
    private String introduced_on = "";
    private String type = "";
    private String chamber="";
    private String title = "";
    private String fname="", lname="";
    private Boolean active = false;
    private String website="";
    private String version="";
    private String pdf="";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public void setid(String input){
        this.id = input.toUpperCase();
    }
    public String getID(){return id;}

    public void setshorttitle(String input){
        this.shorttitle = input;
    }
    public String getshorttitle(){
        if (shorttitle.isEmpty()){
            return longtitle;
        }else{
            return shorttitle;
        }
    }

    public void setlongtitle(String input){
        this.longtitle = input;
    }

    public void setintroduced_on(String input){
        this.introduced_on = input;
    }
    public String getintroduced_on(){return convertDate(introduced_on);}

    public void settype(String input){
        this.type = input.toUpperCase();
    }
    public String gettype(){return type;}


    public void setChamber(String chamber){
        this.chamber = chamber.substring(0, 1).toUpperCase() + chamber.substring(1);
    }
    public String getChamber(){ return chamber;}

    public void setName(String title, String last_name, String first_name){
        this.title = title;
        this.lname = last_name;
        this.fname = first_name;
    }
    public String getsponsor(){
        return title + ". " + lname + ", " + fname;
    }

    public void setBillActive(){
        active = true;
    }
    public String getBillStatus(){
        if (active)
            return "Active";
        else
            return "New";
    }

    public void setWebsite(String website){
        this.website = website;
    }
    public String getWebsite(){ return website;}

    public void setpdf(String website){
        this.pdf = pdf;
    }
    public String getpdf(){ return pdf;}

    public void setversion(String website){
        this.version = version;
    }
    public String getversion(){ return version;}

    String convertDate(String a){
        String newDate = "";
        switch (a.substring(5,7)) {
            case "01":
                newDate = "Jan";
                break;
            case "02":
                newDate = "Feb";
                break;
            case "03":
                newDate = "Mar";
                break;
            case "04":
                newDate = "Apr";
                break;
            case "05":
                newDate = "May";
                break;
            case "06":
                newDate = "Jun";
                break;
            case "07":
                newDate = "July";
                break;
            case "08":
                newDate = "Aug";
                break;
            case "09":
                newDate = "Sept";
                break;
            case "10":
                newDate = "Oct";
                break;
            case "11":
                newDate = "Nov";
                break;
            case "12":
                newDate = "Dec";
                break;
        }
        newDate += " " + a.substring(8) + ", ";
        newDate += a.substring(0,4);
        return newDate;
    }
}

