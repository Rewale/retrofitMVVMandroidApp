package com.example.movieappmvvmretrofit2.request;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.movieappmvvmretrofit2.AppExecutors;
import com.example.movieappmvvmretrofit2.MailAccept;
import com.example.movieappmvvmretrofit2.body.GoogleLoginBody;
import com.example.movieappmvvmretrofit2.body.MailAcceptBody;
import com.example.movieappmvvmretrofit2.body.MailPasswordBody;
import com.example.movieappmvvmretrofit2.response.AccsesApiTokenResponse;
import com.example.movieappmvvmretrofit2.response.ErrorMessageResponse;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Response;

import static com.example.movieappmvvmretrofit2.utlis.Credentials.SCOPES;

public class LoginApiClient {

    public static LoginApiClient getInstance() {
        if(instance==null){
            instance=new LoginApiClient();
        }
        return instance;
    }

    public MutableLiveData<AccsesApiTokenResponse> getmToken() {
        return mToken;
    }

    private MutableLiveData<AccsesApiTokenResponse> mToken;

    public MutableLiveData<String> getmGoogleToken() {
        return mGoogleToken;
    }

    private MutableLiveData<String> mGoogleToken;

    public MutableLiveData<String> getmErrorMessage() {
        return mErrorMessage;
    }

    private MutableLiveData<String> mErrorMessage;

    private static LoginApiClient instance;



    LoginApiClient()
    {
        mToken=new MutableLiveData<AccsesApiTokenResponse>();
        mGoogleToken = new MutableLiveData<String>();
        mErrorMessage = new MutableLiveData<String>();
    }

    public void loginApi(GoogleLoginBody body)
    {
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<AccsesApiTokenResponse> response = Service.getLoginApi().loginGoogle(body).execute();

                    if(response.code() == 200)
                    {
                        Log.v(Service.tagForLogin, "Данные пришли: "+ response.body().getAccessToken());
                        mToken.postValue(response.body());
                    }
                    else{
                        Log.v("Tag", "Данные пришли: 400 ");
                        String error = response.errorBody().string();
                        Log.v("Tag", "Error "+error);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
//            @Override
//            public void run() {
//                // Cancelling the retrofit call
//                myHandler.cancel(true);
//            }
//        },3000, TimeUnit.MILLISECONDS);
    }

    public void regMailPasswordApi(MailPasswordBody body)
    {
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<String> response = Service.getLoginApi().regMailPassword(body).execute();

                    if(response.code() == 403)
                    {
                        assert response.errorBody() != null;
                        Log.v(Service.tagForLogin, response.errorBody().string());
                        //TODO: Десерилизовать ошибки
                        // TODO: разделить авторизацию и регистрацию
                        // TODO: переход на ввод кода подтверждения(попробовать сделать ссылку)
                        //mErrorMessage.postValue(((ErrorMessageResponse)response.errorBody().toString()));
                        //mToken.postValue(response.body());
                    }
                    else if(response.code() == 200)
                    {
                        mErrorMessage.postValue((response.body()));
                    }
                    else{
                        Log.v("Tag", "Данные пришли: 400 ");
//                        String error = response.errorBody().string();
//                        Log.v("TagRegMail", "Error "+error);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
//            @Override
//            public void run() {
//                // Cancelling the retrofit call
//                myHandler.cancel(true);
//            }
//        },3000, TimeUnit.MILLISECONDS);
    }

    public void AcceptCodeApi(MailAcceptBody body)
    {
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                try{
                    Response<AccsesApiTokenResponse> response = Service.getLoginApi().acceptEmail(body).execute();

                    if(response.code() == 200)
                    {
                        Log.v(Service.tagForLogin, "Данные пришли: "+ response.body().getAccessToken());
                        mToken.postValue(response.body());
                    }
                    else{
                        Log.v("Tag", "Данные пришли: 400 ");
                        String error = response.errorBody().string();
                        Log.v("Tag", "Error "+error);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
//            @Override
//            public void run() {
//                // Cancelling the retrofit call
//                myHandler.cancel(true);
//            }
//        },3000, TimeUnit.MILLISECONDS);
    }


    public void tokenGoogle(Context context, String accountName){
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = GoogleAuthUtil.getToken(context, accountName,
                            SCOPES);
                    getmGoogleToken().postValue(token);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GoogleAuthException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
