package com.kg.vista.beeservice.model;

public class NewRequestModel {

    String user_request_desc;
    String user_approx_cash;
    String user_request_address;
    String user_request_phone_number;




    public NewRequestModel(String user_request_desc, String user_approx_cash, String user_request_address, String user_request_phone_number)
    {
        this.user_request_desc = user_request_desc;
        this.user_approx_cash = user_approx_cash;
        this.user_request_address = user_request_address;
        this.user_request_phone_number = user_request_phone_number;


    }

    public String getUser_request_desc() {
        return user_request_desc;
    }

    public String getUser_approx_cash() {
        return user_approx_cash;
    }

    public String getUser_request_address() {
        return user_request_address;
    }

    public String getUser_request_phone_number() {
        return user_request_phone_number;
    }


}