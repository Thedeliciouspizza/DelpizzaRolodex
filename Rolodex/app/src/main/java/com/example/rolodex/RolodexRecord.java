package com.example.rolodex;

import java.io.Serializable;
import java.util.Random;

public class RolodexRecord implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;

    // Constructor for RolodexRecord
    public RolodexRecord(String firstName, String lastName, String middleName, String phoneNumber) {
        Random rand = new Random();
        this.id = String.valueOf(rand.nextInt(1000000));
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
    }

    public RolodexRecord(String id, String firstName, String lastName, String middleName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName(){
        String strName = getLastName()+ ", " + getFirstName() + " " + getMiddleName();
        return strName;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
