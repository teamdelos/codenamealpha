package com.cmu.delos.codenamealpha.model;

/**
 * Created by Vignan on 7/19/2015.
 */
public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String image;
    private String isProvider;
    private String gender;
    private String mobileNum;

    public User(int userId,String firstName,String lastName,String email,String isProvider){
        this.userId=userId;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.isProvider=isProvider;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsProvider() {
        return isProvider;
    }

    public void setIsProvider(String isProvider) {
        this.isProvider = isProvider;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }
}
