package com.example.banice.laundry254;


public class get_details {
    private String name;
    private String password;
    private String username;
    private String email;
    private String image;
    private String userType;

    get_details(String name, String password, String username, String email, String image, String userType) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.email = email;
        this.image = image;
        this.userType = userType;
    }

    public get_details() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}