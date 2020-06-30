package com.weithink.fengkong.bean;

public class ContactsInfo {
    private String phoneId;
    private String name;
    private String number;
    private String firstName;
    private String middleName;
    private String lastName;
    private String company;
    private String position;
    private String remarks;

    public String getPhoneId() {
        return this.phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String toString() {
        return "ContactsInfo{phoneId='" + this.phoneId + '\'' + ", name='" + this.name + '\'' + ", number='" + this.number + '\'' + ", firstName='" + this.firstName + '\'' + ", middleName='" + this.middleName + '\'' + ", lastName='" + this.lastName + '\'' + ", company='" + this.company + '\'' + ", position='" + this.position + '\'' + ", remarks='" + this.remarks + '\'' + '}';
    }
}


