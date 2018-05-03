package com.mersiyanov.dmitry.yadg.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mersiyanov.dmitry.yadg.R;
import com.mersiyanov.dmitry.yadg.YadApplication;
import com.mersiyanov.dmitry.yadg.ui.mvp.MainActivity;
import com.yandex.authsdk.YandexAuthException;
import com.yandex.authsdk.YandexAuthOptions;
import com.yandex.authsdk.YandexAuthSdk;
import com.yandex.authsdk.YandexAuthToken;

import java.util.HashSet;
import java.util.Set;

public class AuthActivity extends AppCompatActivity {

    private TextView noAuthLogin;
    private Button authBtn;
    private static final int REQUEST_CODE_YAD_LOGIN = 10;
    private YandexAuthSdk sdk;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (((YadApplication) getApplication()).isLoggedIn()){
            startMainActivity();

        } else {
            authBtn = findViewById(R.id.button_auth);
            authBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            noAuthLogin = findViewById(R.id.no_auth_login);
            noAuthLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((YadApplication) getApplication()).setAuthToken("AQAAAAAAyzDHAATzcz1kz5Y72UNGurKlusPcD5c");
                    startMainActivity();
                }
            });
        }
    }

    private void login() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.auth_in_progress));
        progressDialog.show();

        Set<String>  scope = new HashSet<>();
        sdk = new YandexAuthSdk(new YandexAuthOptions(AuthActivity.this, true));
        startActivityForResult(sdk.createLoginIntent(AuthActivity.this, scope), REQUEST_CODE_YAD_LOGIN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        progressDialog.dismiss();
        if (requestCode == REQUEST_CODE_YAD_LOGIN) {
            if (resultCode == RESULT_OK) {
                try {
                    YandexAuthToken yandexAuthToken = sdk.extractToken(resultCode, data);
                    if (yandexAuthToken != null) {
                        ((YadApplication) getApplication()).setAuthToken(yandexAuthToken.getValue());
                        Log.e("TOKEN", yandexAuthToken.getValue());
                        startMainActivity();
                    }
                } catch (YandexAuthException e) {
                    Log.e(this.toString(), e.getLocalizedMessage());
                }
            }

            else {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_LONG).show();
            }
            super.onActivityResult(requestCode, resultCode, data);
            }
    }

    public void startMainActivity() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
