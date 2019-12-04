package com.edufy.eddufy;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RegistrationInformation {

    public String id;
    public String name;
    public String email;
    public String gender;
    public String course;
    public String institution;
    public String userId;

    public RegistrationInformation(){
        //require empty constructor
    }

    public RegistrationInformation(String id, String name, String email, String gender, String course, String institution, String userId){
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.course = course;
        this.institution = institution;
        this.userId = userId;

    }

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }
    public String getGender(){
        return gender;
    }
    public String getCourse(){
        return course;
    }
    public String getInstitution(){
        return institution;
    }
    public String getuserId(){
        return userId;
    }
}
