package com.kg.vista.beeservice.model;

public class NewRequestModel {

    String description;
    Integer price;
    int id_;


    public NewRequestModel(String description, int id_, Integer price) {
        this.description = description;
        this.id_ = id_;
        this.price = price;

    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

}