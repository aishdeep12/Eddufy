package com.edufy.eddufy;

public class PostInfo {


    public PostInfo(){

    }
    public String userEmail;
    public String post;
    public String userId;

    public String getUserEmail() {
        return userEmail;
    }

    public String getPost() {
        return post;
    }

    public String getUserId() {
        return userId;
    }

    public PostInfo(String userEmail, String post, String userId){
        this.userId = userId;
        this.userEmail = userEmail;
        this.post = post;
    }
}
