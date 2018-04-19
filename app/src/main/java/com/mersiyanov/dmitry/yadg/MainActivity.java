package com.mersiyanov.dmitry.yadg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.mersiyanov.dmitry.yadg.network.RetroHelper;
import com.mersiyanov.dmitry.yadg.network.YaDiskApi;
import com.yandex.authsdk.YandexAuthException;
import com.yandex.authsdk.YandexAuthOptions;
import com.yandex.authsdk.YandexAuthSdk;
import com.yandex.authsdk.YandexAuthToken;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String AUTHORIZE = "https://oauth.yandex.ru";
    private static final String ENDPOINT = "https://cloud-api.yandex.net/v1/disk/";
    private static final int REQUEST_CODE_YAD_LOGIN = 01;
    private Set<String> scope;
    private YandexAuthSdk sdk;
    private YandexAuthToken yandexAuthToken;

    String url = "https://oauth.yandex.ru/authorize?response_type=token&client_id=2b2675e7f1a84977b8f2a0510434694a";
    String token = "AQAAAAAAyzDHAATzcz1kz5Y72UNGurKlusPcD5c";

    List<ResponseFileList.Item> itemList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scope = new HashSet<>();
        sdk = new YandexAuthSdk(new YandexAuthOptions(this, true));
        startActivityForResult(sdk.createLoginIntent(this, scope), REQUEST_CODE_YAD_LOGIN);







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_YAD_LOGIN) {
            try {
                yandexAuthToken = sdk.extractToken(resultCode, data);
                if (yandexAuthToken != null) {
                    Toast.makeText(this, "Токен получен " + yandexAuthToken.getValue(), Toast.LENGTH_LONG).show();
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
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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
