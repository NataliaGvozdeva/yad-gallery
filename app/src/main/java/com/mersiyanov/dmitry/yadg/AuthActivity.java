package com.mersiyanov.dmitry.yadg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mersiyanov.dmitry.yadg.network.RetroHelper;
import com.yandex.authsdk.YandexAuthException;
import com.yandex.authsdk.YandexAuthOptions;
import com.yandex.authsdk.YandexAuthSdk;
import com.yandex.authsdk.YandexAuthToken;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthActivity extends AppCompatActivity {

    Button authBtn;

    private static final int REQUEST_CODE_YAD_LOGIN = 10;
    private Set<String> scope;
    private YandexAuthSdk sdk;
    private YandexAuthToken yandexAuthToken;

    List<ResponseFileList.Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);



        authBtn = findViewById(R.id.button_auth);
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scope = new HashSet<>();
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

                    RetroHelper.getApi().getPlainFileList().subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<ResponseFileList>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(ResponseFileList responseFileList) {
                                    itemList = responseFileList.getItems();
                                    System.out.println(itemList.get(1).getPath());

                                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(AuthActivity.this, R.string.load_error, Toast.LENGTH_LONG).show();
                                    Log.e("onError", e.getLocalizedMessage());

                                }
                            });
                }
            } catch (YandexAuthException e) {
                Log.e(this.toString(), e.getLocalizedMessage());
            }
            return;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
