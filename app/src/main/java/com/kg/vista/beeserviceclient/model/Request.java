package com.kg.vista.beeserviceclient.model;

import java.util.ArrayList;
import java.util.List;

public class Request {

    public int requestId;
    public String requestSubcategory;

    public String requestDescription;

    public String requestPrice;

    public String requestOrderTime;

    public String requestPhoneNumber;

    public Request(int requestId, String requestSubcategory,  String requestDescription, String requestPrice, String requestOrderTime, String requestPhoneNumber) {
        this.requestId = requestId;
        this.requestSubcategory = requestSubcategory;
        this.requestDescription = requestDescription;
        this.requestPrice = requestPrice;
        this.requestOrderTime = requestOrderTime;
        this.requestPhoneNumber = requestPhoneNumber;
    }
}