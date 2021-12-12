package com.example.movieappmvvmretrofit2.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccsesApiTokenResponse {

    public String getAccessToken() {
        return accessToken;
    }

    @SerializedName("access_token")
    @Expose
    private String accessToken;


}

