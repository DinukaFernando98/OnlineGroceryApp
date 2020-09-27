package com.example.grocery.models;

public class ModelShop {
    private String uid, email, name, shopName, phoneNumber, address, deliveryFee, timestamp, profileImage, accountType;

    public ModelShop() {
    }

    public ModelShop(String uid, String email, String name, String shopName, String phoneNumber, String address, String deliveryFee, String timestamp, String profileImage, String accountType) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.deliveryFee = deliveryFee;
        this.timestamp = timestamp;
        this.profileImage = profileImage;
        this.accountType = accountType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
