package com.example.assignment24;

public class CardModel {

    private String cardNumber;
    private String cvv;
    private String expiry;
    private String cardName;

    // Required empty constructor for Firestore
    public CardModel() {
    }

    // Constructor with all fields
    public CardModel(String cardNumber, String cvv, String expiry, String cardName) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiry = expiry;
        this.cardName = cardName;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}


