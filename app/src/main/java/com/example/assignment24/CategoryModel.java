package com.example.assignment24;

import java.util.Map;

public class CategoryModel {

    String documentId;  // Added field for storing Firestore document ID
    String img_url1;
    String name;
    String price;
    String description;
    String type;

    // Existing constructor for creating an instance with explicit values
    public CategoryModel(String documentId, String img_url1, String name, String price, String description, String type) {
        this.documentId = documentId;
        this.img_url1 = img_url1;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }


    public CategoryModel(Map<String, Object> data) {
        if (data != null) {
            this.documentId = (String) data.get("documentId");
            this.img_url1 = (String) data.get("img_url1");
            this.name = (String) data.get("name");
            this.price = (String) data.get("price");
            this.description = (String) data.get("description");
            this.type = (String) data.get("type");
        }
    }

    public CategoryModel() {
        // Default constructor
    }


    // Getter and setter methods for all fields
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getImg_url1() {
        return img_url1;
    }

    public void setImg_url1(String img_url1) {
        this.img_url1 = img_url1;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

