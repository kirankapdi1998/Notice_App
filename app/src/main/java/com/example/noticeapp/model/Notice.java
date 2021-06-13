package com.example.noticeapp.model;

import com.google.firebase.database.Exclude;

import java.security.Timestamp;
import java.util.Date;

public class Notice {
    String title;
    String descrp;
    String upload;
    String type;
    String dept;
//    Timestamp time;

//    public Notice(String title, String descrp, String upload, String type) {
//        this.title = title;
//        this.descrp = descrp;
//        this.upload = upload;
//        this.type = type;
////        this.time = time;
//    }

    public Notice() {
    }

    public Notice(String title, String Descrp){
        this.title = title;
        this.descrp = Descrp;
    }

    public Notice(String title, String descrp, String upload) {
        this.title = title;
        this.descrp = descrp;
        this.upload = upload;
    }


    public Notice(String title, String descrp, String upload, String type, String dept) {
        this.title = title;
        this.descrp = descrp;
        this.upload = upload;
        this.type = type;
        this.dept = dept;
    }

    public Notice(String title, String descrp, String upload, String type) {
        this.title = title;
        this.descrp = descrp;
        this.upload = upload;
        this.type = type;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public Timestamp getTime() {
//        return time;
//    }
//
//    public void setTime(Timestamp time) {
//        this.time = time;
//    }


    public void setTitle(String title){
        this.title = title;
    }
    public void setDescrp(String descrp){
        this.descrp = descrp;
    }

    public String getTitle(){
        return this.title;
    }
    public String getDescrp(){
        return this.descrp;
    }

    public String getUpload(){
        return this.upload;
    }

    public void setUpload(String upload){
        this.upload = upload;
    }
}
