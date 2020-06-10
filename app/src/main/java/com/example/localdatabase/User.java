package com.example.localdatabase;

public class User {
    /**the user's ID */
    int id;
    /**the user's name, job title, age and gender,*/
    String name,jobTitle, gender, age;;

    /**Create constructor*/
    public User(int id, String name, String age, String jobTitle, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.jobTitle = jobTitle;
        this.gender = gender;
    }
    /**empty constructor*/
    public User(){}
    //return the id of the user
    public int getId() {
        return id;
    }

    //set the id of the user
    public void setId(int id) {
        this.id = id;
    }

    //return the name of the user
    public String getName() {
        return name;
    }

    //set the name of the user
    public void setName(String name) {
        this.name = name;
    }

    //return the age of the user
    public String getAge() {
        return age;
    }

    //set the age of the user
    public void setAge(String age) {
        this.age = age;
    }

    //return the job title of the user
    public String getJobTitle() {
        return jobTitle;
    }

    //set the job title of the user
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    //return the gender of the user
    public String getGender() {
        return gender;
    }

    //set the gender of the user
    public void setGender(String gender) {
        this.gender = gender;
    }
}
