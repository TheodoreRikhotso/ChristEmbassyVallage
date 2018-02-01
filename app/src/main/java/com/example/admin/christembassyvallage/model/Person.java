package com.example.admin.christembassyvallage.model;

/**
 * Created by Admin on 01-Nov-17.
 */

public class Person {
    private String id, name, surname,contacts,email,password,imageUrl,role,title,dob, quotes,address, occupation;
    private int rolePosition,titlePosition;
    private boolean like;

    public Person() {
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDob() {
        return dob;
    }

    public String getOccupation() {
        return occupation;
    }


    public int getRolePosition() {
        return rolePosition;
    }

    public void setRolePosition(int rolePosition) {
        this.rolePosition = rolePosition;
    }

    public int getTitlePosition() {
        return titlePosition;
    }

    public void setTitlePosition(int titlePosition) {
        this.titlePosition = titlePosition;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
