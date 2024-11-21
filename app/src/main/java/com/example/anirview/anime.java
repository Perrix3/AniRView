package com.example.anirview;

public class anime {
    public String title;
    public String subtitle;
    public String review;
    public double rating;
    public String imageUrl;

    public anime(String title, String subtitle, String review, double rating, String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.review = review;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getTitle(){
        return this.title;
    }

    public String getSubtitle(){
        return  this.subtitle;
    }

    public String getReview(){
        return this.review;
    }

    public double getRating(){
        return this.rating;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    // Setters
    public void setTitle(String title){
        this.title=title;
    }

    public void setSubtitle(String subtitle){
        this.subtitle=subtitle;
    }

    public void setReview(String review){
        this.review=review;
    }

    public void setRating(double rating){
        this.rating=rating;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
}
