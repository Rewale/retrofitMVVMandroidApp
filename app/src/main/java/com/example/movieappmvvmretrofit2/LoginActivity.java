package com.example.movieappmvvmretrofit2;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import static com.example.movieappmvvmretrofit2.utlis.Credentials.SCOPES;
import static com.example.movieappmvvmretrofit2.utlis.Credentials.TokenApi;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "ErrorAuthGoogle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View btn = (View) findViewById(R.id.sign_in_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                        false, null, null, null, null);
                startActivityForResult(intent, 123);
            }
        });
    }


    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            AsyncTask<Void, Void, String[]> getToken = new AsyncTask<Void, Void, String[]>() {
                @SuppressLint("StaticFieldLeak")
                @Override
                protected String[] doInBackground(Void... params) {
                    try {
                        String[] data =new String[]{GoogleAuthUtil.getToken(LoginActivity.this, accountName,
                                SCOPES), accountName};
                        return data;

                    } catch (UserRecoverableAuthException userAuthEx) {
                        startActivityForResult(userAuthEx.getIntent(), 123);
                    } catch (IOException ioEx) {
                        Log.d(TAG, "IOException");
                    } catch (GoogleAuthException fatalAuthEx) {
                        Log.d(TAG, "Fatal Authorization Exception" + fatalAuthEx.getLocalizedMessage());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String[] data) {
                    reg(data);
                }

            };
            getToken.execute(null, null, null);
        }
    }

    private void reg(String[] data) {
        AsyncTask<Void,Void, String> sendToken = new AsyncTask<Void, Void, String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(Void... voids) {
                try {


                    // debug //URL url = new URL("http://10.0.2.2:58905/api/Values?value="+sendX+":"+sendY);

                    URL url = new URL("http://192.168.1.44:57792/api/v1/auth/google/");//relase
                    URLConnection con = url.openConnection();
                    con.setUseCaches(false);
                    HttpURLConnection http = (HttpURLConnection) con;
                    http.setRequestMethod("POST"); // PUT is another valid option

                    String dataJson = "{ 'email':'"+data[1]+"','token':'"+data[0]+"'}";

                    //http.setDoOutput(true);


                    byte[] out = (dataJson).getBytes(StandardCharsets.UTF_8);
                    int length = out.length;

                    http.setFixedLengthStreamingMode(length);
                    http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    http.connect();

                    try(OutputStream os = http.getOutputStream()) {
                        os.write(out);
                    }

                    String tokenDjango = http.getResponseMessage();

                    http.disconnect();

                    TokenApi = tokenDjango;

                    Log.d(TAG, tokenDjango);

                    return tokenDjango;

                }
                catch (Exception ex)
                {
                    ex.toString();
                }
                return null;
            }
        };

        sendToken.execute(null,null,null);
    }
}
