package com.mersiyanov.dmitry.yadg.mvp;

import com.mersiyanov.dmitry.yadg.network.NetworkUtils;
import com.mersiyanov.dmitry.yadg.network.RetroHelper;
import com.mersiyanov.dmitry.yadg.pojo.ResponseFileList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MainRepo implements PicturesContract.Repo {

    private Realm realm;
    private final RetroHelper retroHelper;
    private Single<ResponseFileList> cache;

    public MainRepo(RetroHelper retroHelper) {
        this.retroHelper = retroHelper;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Single<ResponseFileList> load() {

        if(cache == null) {

            cache = retroHelper.getApi().getImagesList(NetworkUtils.getAuthToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache();
            saveToDb();
    }
        return cache;
    }

    private void saveToDb() {
        cache.subscribe(new SingleObserver<ResponseFileList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseFileList list) {
                realm.beginTransaction();
                realm.insert(list.getItems());
                realm.commitTransaction();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void closeDB() {
        realm.close();
    }
}
