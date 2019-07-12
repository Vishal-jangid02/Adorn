package com.example.vishal.Adorn.MyPojo;

import java.util.ArrayList;
import java.util.List;

public class Request {

    public Request(String email, String name, String address, String total,ArrayList<orderpojo> furnitures) {
        this.phone=phone;
        this.email = email;
        this.name = name;
        this.address = address;
        this.total = total;
        this.status="0";
        this.furnitures = furnitures;

    }
    private String phone;
    private String email;
    private String  name;
    private String address;
    private String total;
    private String status;

    private ArrayList<orderpojo> furnitures;

    public Request()
    {

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() { return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<orderpojo> getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(ArrayList<orderpojo> furnitures) {
        this.furnitures = furnitures;
    }
}
