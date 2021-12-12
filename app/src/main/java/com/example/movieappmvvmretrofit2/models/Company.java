package com.example.movieappmvvmretrofit2.models;

import android.os.Parcelable;

public class Company {
    private String icon;
    private String name;
    private int discount_for_INF;
    private int discount_for_CHD;

    public Company(String icon, String name, int discount_for_INF, int discount_for_CHD) {
        this.icon = icon;
        this.name = name;
        this.discount_for_INF = discount_for_INF;
        this.discount_for_CHD = discount_for_CHD;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscount_for_INF() {
        return discount_for_INF;
    }

    public void setDiscount_for_INF(int discount_for_INF) {
        this.discount_for_INF = discount_for_INF;
    }

    public int getDiscount_for_CHD() {
        return discount_for_CHD;
    }

    public void setDiscount_for_CHD(int discount_for_CHD) {
        this.discount_for_CHD = discount_for_CHD;
    }
}
