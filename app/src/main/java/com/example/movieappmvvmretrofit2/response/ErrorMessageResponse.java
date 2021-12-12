package com.example.movieappmvvmretrofit2.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorMessageResponse{

    public String getDetail() {
        return detail;
    }

    @SerializedName("detail")
    @Expose
    private String detail;
}
