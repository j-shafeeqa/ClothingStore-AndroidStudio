package com.example.assignment24;

public class AddressModel {
    private String userId;
    private String buildingName;
    private String roomNumber;
    private String floorNumber;
    private String streetName;
    private String additionalDirections;
    private String phoneNumber;
    private String addressType;

    // Default constructor required for Firestore
    public AddressModel() {
    }

    // Constructor with arguments
    public AddressModel(String userId, String buildingName, String roomNumber, String floorNumber,
                        String streetName, String additionalDirections, String phoneNumber,
                        String addressType) {
        this.userId = userId;
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.streetName = streetName;
        this.additionalDirections = additionalDirections;
        this.phoneNumber = phoneNumber;
        this.addressType = addressType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getters and setters
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAdditionalDirections() {
        return additionalDirections;
    }

    public void setAdditionalDirections(String additionalDirections) {
        this.additionalDirections = additionalDirections;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}

