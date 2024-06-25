package com.example.egear.customer.profile;

public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String address;

    public UserProfile() {
    }

    public UserProfile(Long id, String username, String name, String description, String email, String phone, String address) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
