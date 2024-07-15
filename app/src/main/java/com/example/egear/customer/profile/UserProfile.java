package com.example.egear.customer.profile;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String address;
    private String avatar_url;
    private Address address_lng_lat;

    public UserProfile() {
    }

    public UserProfile(Long id, String username, String name, String description, String email, String phone, String address, String avatar_url, Address address_lng_lat) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatar_url = avatar_url;
        this.address_lng_lat = address_lng_lat;
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

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Address getAddress_lng_lat() {
        return address_lng_lat;
    }

    public void setAddress_lng_lat(Address address_lng_lat) {
        this.address_lng_lat = address_lng_lat;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", address_lng_lat=" + address_lng_lat +
                '}';
    }
}
