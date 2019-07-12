package com.example.vishal.Adorn.MyPojo;

public class RetrivePojo {
    String id;
    String category;
    String price;
    String uploadImage;
    String name;
    String color1;

    String color2;
    String color3;


    public RetrivePojo(String id, String category, String price, String uploadImage, String name, String color1, String color2, String color3) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.uploadImage = uploadImage;
        this.name = name;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String prize) {
        this.price = price;
    }

    public String getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(String uploadImage) {
        this.uploadImage = uploadImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public RetrivePojo(){

    }

}
