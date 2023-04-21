package com.e_commerce.ecommerce;

public class customer {
    private int id;
    private String name, email, mobile;

    public customer(int id, String name, String email, String mobile){
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
