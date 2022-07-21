package com.example.store.model;

import com.orm.SugarRecord;

public class Products extends SugarRecord {

    public int id;
    public String name;
    public String price;
    public String img1;
    public String img2;
    public String img3;
    public String description;
    public int count =1;
    public String tPrice;

    public String gettPrice() {
        return tPrice;
    }

    public void settPrice(String tPrice) {
        this.tPrice = tPrice;
    }

    public Products() {

    }

    public Products(int id) {
        this.id = id;

    }

    public Products(int id, String name, String price, String description, int count, String tprice, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.count = count;
        this.tPrice = tprice;
        this.img1= image;


    }

    public Products(int id, String name, String price, String description , String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.img1= image;

    }

    public void setId(int id) {
        this.id = id;
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

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
