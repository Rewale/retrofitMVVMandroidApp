package com.example.movieappmvvmretrofit2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.movieappmvvmretrofit2.body.MailAcceptBody;
import com.example.movieappmvvmretrofit2.viewModels.LoginViewModel;

public class MailAccept extends AppCompatActivity {

    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_accept);
        TextView textView =  findViewById(R.id.textView2);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Bundle arguments = getIntent().getExtras();

        textView.setText(textView.getText()+"\n"+arguments.get("mail").toString());

        Button button = findViewById(R.id.accept_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailAcceptBody body = new MailAcceptBody();
                EditText editText = (EditText)findViewById(R.id.acceptCode);
                body.code = editText.getText().toString();
                body.email = arguments.get("mail").toString();
                loginViewModel.getLoginApiClient().AcceptCodeApi(body);
                finish();
            }
        });
    }
}