package com.example.movieappmvvmretrofit2.utlis;

import com.example.movieappmvvmretrofit2.body.GoogleLoginBody;
import com.example.movieappmvvmretrofit2.body.MailAcceptBody;
import com.example.movieappmvvmretrofit2.body.MailPasswordBody;
import com.example.movieappmvvmretrofit2.response.AccsesApiTokenResponse;
import com.example.movieappmvvmretrofit2.response.ErrorMessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {

    // Авторизация через гугл
    @POST("/api/v1/auth/google/")
    Call<AccsesApiTokenResponse> loginGoogle(
            @Body GoogleLoginBody googleLoginBody
    );


    // Регистрация через почту пароль
    @POST("/api/v1/auth/mail_pass_reg/")
    Call<String> regMailPassword(
            @Body MailPasswordBody mailPasswordBody
    );

    // Подтверждение почты
    @POST("/api/v1/auth/mail_pass_accept/")
    Call<AccsesApiTokenResponse> acceptEmail(
            @Body MailAcceptBody mailAcceptBody
    );

    // Авторизация через почту пароль
    @POST("/api/v1/auth/mail_pass_auth/")
    Call<AccsesApiTokenResponse> authMailPassword(
            @Body MailPasswordBody mailPasswordBody
    );
}
