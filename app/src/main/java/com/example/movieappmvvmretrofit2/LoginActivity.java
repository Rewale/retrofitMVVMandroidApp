package com.example.movieappmvvmretrofit2;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieappmvvmretrofit2.body.GoogleLoginBody;
import com.example.movieappmvvmretrofit2.body.MailPasswordBody;
import com.example.movieappmvvmretrofit2.models.MovieModel;
import com.example.movieappmvvmretrofit2.request.Service;
import com.example.movieappmvvmretrofit2.response.AccsesApiTokenResponse;
import com.example.movieappmvvmretrofit2.response.ErrorMessageResponse;
import com.example.movieappmvvmretrofit2.viewModels.LoginViewModel;
import com.example.movieappmvvmretrofit2.viewModels.MovieListViewModel;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.example.movieappmvvmretrofit2.utlis.Credentials.SCOPES;
import static com.example.movieappmvvmretrofit2.utlis.Credentials.TokenApi;

public class LoginActivity extends AppCompatActivity {

    private static final String SAVED_TOKEN = "TokenApi";
    private LoginViewModel loginViewModel;
    String accountName;
    private GoogleSignInClient mGoogleSignInClient;
    EditText mail, password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        View btn = (View) findViewById(R.id.sign_in_button);
        Button signInMailPass = findViewById(R.id.login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, 123);
            }
        });

        signInMailPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mail = findViewById(R.id.username);
                password = findViewById(R.id.password);
                MailPasswordBody body = new MailPasswordBody();
                body.email = mail.getText().toString();
                body.password = password.getText().toString();

                loginViewModel.getLoginApiClient().regMailPasswordApi(body);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("829308427010-nhn07sasokgsic2qbf45g7difk1cjndq.apps.googleusercontent.com") // Хранить лучше в string.xml
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
            Log.v(Service.tagForLogin,account.getEmail());

        ObserverAnyChange();
    }



    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
//            accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//
//            loginViewModel.getLoginApiClient().tokenGoogle(LoginActivity.this, accountName);

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }

    }
    // TODO: стоило бы поместить в loginApiClient
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String token = account.getIdToken();
            Log.d(Service.tagForLogin, token);
            GoogleLoginBody body = new GoogleLoginBody(account.getEmail(), token );
            loginViewModel.getLoginApiClient().loginApi(body);
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(Service.tagForLogin, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void RewriteToken(String token)
    {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TOKEN, token);
        ed.commit();
    }

    // Observing any data change
    private void ObserverAnyChange(){
        loginViewModel.getLoginApiClient().getmToken().observe(this, new Observer<AccsesApiTokenResponse>() {
            @Override
            public void onChanged(AccsesApiTokenResponse accsesApiTokenResponse) {
                // Слушатель изменений
                if(accsesApiTokenResponse != null){
                    Log.v(Service.tagForLogin, accsesApiTokenResponse.getAccessToken());
                    RewriteToken(accsesApiTokenResponse.getAccessToken());
                    //TODO: Открытие общего окна - профиль, выбор рейсов с фильтором по аэропортам
                    Intent intent = new Intent(LoginActivity.this, FlightsSearchActivity.class);
                    //intent.putExtra("mail", mail.getText().toString());
                    startActivity(intent);
                }
            }


        });

        loginViewModel.getLoginApiClient().getmGoogleToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null) {
                    GoogleLoginBody googleLoginBody = new GoogleLoginBody(accountName, s);
                    googleLoginBody.email = accountName;
                    googleLoginBody.token = s;
                    loginViewModel.getLoginApiClient().loginApi(googleLoginBody);
                }
            }
        });

        loginViewModel.getLoginApiClient().getmErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    if(s.equals("Для продолжения подтвердите учетную запись")) {
                        Intent intent = new Intent(LoginActivity.this, MailAccept.class);
                        intent.putExtra("mail", mail.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), s + "", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


}
