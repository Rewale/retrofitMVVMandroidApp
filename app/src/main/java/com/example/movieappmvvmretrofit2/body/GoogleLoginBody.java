package com.example.movieappmvvmretrofit2.body;

public class GoogleLoginBody {

    public String email;
    public String token;

    public GoogleLoginBody(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
