package com.example.assignment24;

public class MyCartModel {
    String name;
    String price;
    String totalQuantity;
    String totalPrice;
    String imageUrl;
    String documentId;


    public MyCartModel() {
    }

    public MyCartModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MyCartModel(String name, String price, String totalQuantity, String totalPrice) {
        this.name = name;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
