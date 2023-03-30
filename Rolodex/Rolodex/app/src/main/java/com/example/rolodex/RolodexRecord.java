package com.example.rolodex;

import java.io.Serializable;

public class RolodexRecord implements Serializable {
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;

    // Constructor for RolodexRecord
    public RolodexRecord(String firstName, String lastName, String middleName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
    }
    public int getId() {
        // Return a unique code
        return (firstName + lastName).hashCode();
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
