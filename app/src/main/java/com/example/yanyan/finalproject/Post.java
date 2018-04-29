package com.example.yanyan.finalproject;

import android.content.Context;

import java.util.Comparator;

/**
 * Created by yanyan on 4/8/18.
 */

public class Post {

    //// instant variable or fields
    public int id;
    public String booktitle;
    public String quote;
    public String author;
    public String rate;
    public String hashtag;
    public String Reviews;
//    public String Audiopath;
    public byte[] Bookcover;
    public String Datetime;
    public double ratedouble;
    public String fetch;

//    public Post(String booktitle, String author, String hashtag, String quote, String rate,
//                String Reviews,String Audiopath,String Bookcover, String Datetime,String fetch){
public Post(String booktitle, String author, String hashtag, String quote, String rate,
            String Reviews, byte[] Bookcover, String Datetime){
//String book, String author, String tags, String quote, String rate, String thoughts,String record,String bookcover, String datetime


        this.id = id;
        this.booktitle = booktitle;
        this.quote = quote;
        this.author = author;
        this.rate = rate;
        this.hashtag = hashtag;
        this.Reviews = Reviews;
//        this.Audiopath = Audiopath;
        this.Bookcover = Bookcover;
        this.Datetime = Datetime;
        this.ratedouble = Double.parseDouble(rate);
//        this.fetch = fetch;
    }


//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }

    public String gettitle() {
        return booktitle;
    }
    public void settitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getquote() {
        return quote;
    }
    public void setquote(String quote) {
        this.quote = quote;
    }

    public String getauthor() {
        return author;
    }
    public void setauthor(String author) {
        this.author = author;
    }

    public String getrate() {
        return rate;
    }
    public void setrate(String rate) {
        this.rate = rate;
    }


    public String gethashtag() {
        return hashtag;
    }
    public void sethashtag(String hashtag) {
        this.hashtag = hashtag;
    }


    public String getReviews() {
        return Reviews;
    }
    public void setReviews(String Reviews) {
        this.Reviews = Reviews;
    }

//    public String getAudiopath() {
//        return Audiopath;
//    }
//    public void setAudiopath(String Audiopath) {
//        this.Audiopath = Audiopath;
//    }

    public byte[] getBookcover() {
        return Bookcover;
    }
    public void setBookcover(byte[] Bookcover) {
        this.Bookcover = Bookcover;
    }

    public String getDatetime() {
        return Datetime;
    }
    public void setDatetime(String Datetime) {
        this.Datetime = Datetime;
    }

    public String getfetch() {
        return fetch;
    }
    public void setfetch(String fetch) {this.fetch = fetch;}



//    public static Comparator<Post> ratecompare = new Comparator<Post>() {
//
//        public int compare(Post p1, Post p2) {
//
//            int p1rate = p1.rateint;
//            int p2rate = p2.rateint;
//
//	   /*For ascending order*/
//            return p1rate-p2rate;
//
//	   /*For descending order*/
//            //rollno2-rollno1;
//        }};










}
