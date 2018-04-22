package com.mersiyanov.dmitry.yadg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yandex.authsdk.YandexAuthException;
import com.yandex.authsdk.YandexAuthOptions;
import com.yandex.authsdk.YandexAuthSdk;
import com.yandex.authsdk.YandexAuthToken;

import java.util.HashSet;
import java.util.Set;

public class AuthActivity extends AppCompatActivity {

    Button authBtn;

    private static final int REQUEST_CODE_YAD_LOGIN = 10;
    private YandexAuthSdk sdk;
    private YandexAuthToken yandexAuthToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authBtn = findViewById(R.id.button_auth);
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String>  scope = new HashSet<>();
                sdk = new YandexAuthSdk(new YandexAuthOptions(AuthActivity.this, true));
                startActivityForResult(sdk.createLoginIntent(AuthActivity.this, scope), REQUEST_CODE_YAD_LOGIN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_YAD_LOGIN) {
            try {
                yandexAuthToken = sdk.extractToken(resultCode, data);
                if (yandexAuthToken != null) {
//                    Toast.makeText(this, "Токен получен " + yandexAuthToken.getValue(), Toast.LENGTH_LONG).show();
//                    Log.d("token", yandexAuthToken.getValue());

                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);


                }
            } catch (YandexAuthException e) {
                Log.e(this.toString(), e.getLocalizedMessage());
            }
            return;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
