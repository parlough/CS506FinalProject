package com.example.voteforyou;

//Class used to define a user. All information about a user can be stored in a User object.
public class User {

    //user has name and email
    public String name, email;

    //default constructor
    public User(){

    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

}
