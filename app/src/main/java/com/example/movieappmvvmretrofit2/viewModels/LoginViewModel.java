package com.example.movieappmvvmretrofit2.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.movieappmvvmretrofit2.request.LoginApiClient;

public class LoginViewModel extends ViewModel {

    public LoginApiClient getLoginApiClient() {
        return loginApiClient;
    }

    private LoginApiClient loginApiClient;

    public LoginViewModel()
    {
        loginApiClient = LoginApiClient.getInstance();
    }



}
