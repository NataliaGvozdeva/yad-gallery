package com.mersiyanov.dmitry.yadgallery.ui.mvp;

import com.mersiyanov.dmitry.yadgallery.network.RetroHelper;
import com.mersiyanov.dmitry.yadgallery.pojo.Item;
import com.mersiyanov.dmitry.yadgallery.pojo.ResponseFileList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    public Single<ResponseFileList> load(String token) {

        if(cache == null) {
            cache = retroHelper.getApi().getImagesList(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache()
                    .doOnSuccess(list -> saveToDb(list));
    }
        return cache;
    }

    private void saveToDb(ResponseFileList list){
        if(realm.where(Item.class).findAll().size() == 0) {
            realm.beginTransaction();
            realm.insert(list.getItems());
            realm.commitTransaction();
        }
    }

    @Override
    public void closeDB() {
        realm.close();
    }

    @Override
    public void initDB() {
        realm = Realm.getDefaultInstance();
    }
}
