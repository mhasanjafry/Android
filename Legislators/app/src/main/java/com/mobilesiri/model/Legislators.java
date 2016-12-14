package com.mobilesiri.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mhasa on 11/27/2016.
 */

public class Legislators implements Serializable{

    private String title="", last_name="", first_name="";
    private String website = "";
    private String twitter_id = "";
    private String facebook_id = "";
    private String imageUrl="";
    private String party="", partyPic="", partyfirstChar="";
    private String email="";
    private String chamber="";
    private String district = "";
    private String contact="", fax="";
    private String start_term = "", end_term ="";
    private String office="";
    private String state="", fullstateName="";
    private String birthday="";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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

    public void setStart_term(String input){
        this.start_term = input;
    }
    public String getStart_term(){return convertDate(start_term);}

    public void setEnd_term(String input){
        this.end_term = input;
    }
    public String getEnd_term(){return convertDate(end_term);}

    public void setBirthday(String birthday){
        this.birthday = convertDate(birthday);
    }
    public String getBirthday(){return birthday;}

    public void setName(String title, String last_name, String first_name){
        this.title = title;
        this.last_name = last_name;
        this.first_name = first_name;
    }

    public int getProgress(){
        if (start_term.isEmpty() || end_term.isEmpty()){
            return 0;
        }
        try{
            Date startDate = df.parse(start_term);
            Date endDate = df.parse(end_term);
            double prog = (100.0*(System.currentTimeMillis() - startDate.getTime()))/(endDate.getTime() - startDate.getTime());
            return (int)prog;
        }catch (Exception e){
        }
        return 20;
    }
    public String getLastName()  {
        return last_name;
    }
    public String getName() {
        return last_name + ", " + first_name;
    }
    public String getFullName() {
        return title + ". " + last_name + ", " + first_name;
    }

    public void setParty(String party) {
        this.partyfirstChar = party;
        switch (party) {
            case "R":
                this.party = "Republican";
                this.partyPic = "http://cs-server.usc.edu:45678/hw/hw8/images/r.png";
                break;
            case "D":
                this.party = "Democrat";
                this.partyPic = "http://cs-server.usc.edu:45678/hw/hw8/images/d.png";
                break;
            case "I":
                this.party = "Independent";
                this.partyPic = "http://www.designhill.com/design-blog/wp-content/uploads/2015/05/Independent-American-Party-Logo.png";
                break;
        }
    }
    public String getParty(){return party;}
    public String getPartyPic(){return partyPic;}
    public String getpartyfirstChar(){return partyfirstChar;}

    public void setWebsite(String website){
        this.website = website;
    }
    public String getWebsite(){ return website;}

    public void setContact(String contact){
        this.contact = contact;
    }
    public String getContact(){ return contact;}

    public void setFax(String fax){
        this.fax = fax;
    }
    public String getFax(){ return fax;}

    public void setState(String state){
        this.state = state;
    }
    public String getState(){ return state;}

    public void setFullstateName(String fullstateName){
        this.fullstateName = fullstateName;
    }
    public String getFullstateName(){ return fullstateName;}

    public void setDistrict(String district){
        this.district = district;
    }
    public String getDistrict(){ return district;}


    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){ return email;}

    public void setOffice(String office){
        this.office = office;
    }
    public String getOffice(){ return office;}

    public void setChamber(String chamber){
        this.chamber = chamber.substring(0, 1).toUpperCase() + chamber.substring(1);
    }
    public String getChamber(){ return chamber;}

    public void setFacebook(String fb_id){
        this.facebook_id = fb_id;
    }
    public String getFacebook(){
        if (facebook_id.isEmpty()){
            return "";
        }
        return "https://www.facebook.com/" + facebook_id;
    }

    public void setTwitter(String twitter_id){
        this.twitter_id = twitter_id;
    }
    public String getTwitter(){
        if (twitter_id.isEmpty()){
            return "";
        }
        return "https://twitter.com/" + twitter_id;
    }

    public void setImgUrl(String bio){
        this.imageUrl = "https://theunitedstates.io/images/congress/original/"+ bio +".jpg";
    }
    public String getImgURL(){ return imageUrl;}

}
