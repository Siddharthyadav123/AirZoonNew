package com.az.airzoon.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * {
 * "id": "6",
 * "name": "Le Point de Vue",
 * "date": "",
 * "address": "Anse Charpentier",
 * "address2": "",
 * "zip": "97230",
 * "country": "Martinique",
 * "city": "Sainte-Marie",
 * "lat": "14.809419885685156",
 * "lng": "-61.02135209580081",
 * "image": "image\/other.jpg",
 * "phone": "0596 69 05 22",
 * "type": "free",
 * "category": "Restaurant",
 * "is_free": "",
 * "speed": "1--5",
 * "fav_count": "0",
 * "category_image": "category\/resturant.png",
 * "opening_one": "--",
 * "opening_two": "--"
 * }
 * Created by siddharth on 7/26/2016.
 */
public class AirZoonDo implements Parcelable {

    private String zip;
    private String phone;
    private String speed;
    private String image;
    private String address2;
    private String lng;
    private String type;
    private String date;
    private String country;
    private String city;
    private String id;
    private String opening_two;
    private String category;
    private String opening_one;
    private String address;
    private String category_image;
    private String name;
    private String fav_count;
    private String lat;
    private String is_free;

    public AirZoonDo() {

    }

    protected AirZoonDo(Parcel in) {
        zip = in.readString();
        phone = in.readString();
        speed = in.readString();
        image = in.readString();
        address2 = in.readString();
        lng = in.readString();
        type = in.readString();
        date = in.readString();
        country = in.readString();
        city = in.readString();
        id = in.readString();
        opening_two = in.readString();
        category = in.readString();
        opening_one = in.readString();
        address = in.readString();
        category_image = in.readString();
        name = in.readString();
        fav_count = in.readString();
        lat = in.readString();
        is_free = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(zip);
        dest.writeString(phone);
        dest.writeString(speed);
        dest.writeString(image);
        dest.writeString(address2);
        dest.writeString(lng);
        dest.writeString(type);
        dest.writeString(date);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(id);
        dest.writeString(opening_two);
        dest.writeString(category);
        dest.writeString(opening_one);
        dest.writeString(address);
        dest.writeString(category_image);
        dest.writeString(name);
        dest.writeString(fav_count);
        dest.writeString(lat);
        dest.writeString(is_free);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AirZoonDo> CREATOR = new Creator<AirZoonDo>() {
        @Override
        public AirZoonDo createFromParcel(Parcel in) {
            return new AirZoonDo(in);
        }

        @Override
        public AirZoonDo[] newArray(int size) {
            return new AirZoonDo[size];
        }
    };

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpening_two() {
        return opening_two;
    }

    public void setOpening_two(String opening_two) {
        this.opening_two = opening_two;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOpening_one() {
        return opening_one;
    }

    public void setOpening_one(String opening_one) {
        this.opening_one = opening_one;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFav_count() {
        return fav_count;
    }

    public void setFav_count(String fav_count) {
        this.fav_count = fav_count;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    @Override
    public String toString() {
        return "ClassPojo [zip = " + zip + ", phone = " + phone + ", speed = " + speed + ", image = " + image + ", address2 = " + address2 + ", lng = " + lng + ", type = " + type + ", date = " + date + ", country = " + country + ", city = " + city + ", id = " + id + ", opening_two = " + opening_two + ", category = " + category + ", opening_one = " + opening_one + ", address = " + address + ", category_image = " + category_image + ", name = " + name + ", fav_count = " + fav_count + ", lat = " + lat + ", is_free = " + is_free + "]";
    }
}
