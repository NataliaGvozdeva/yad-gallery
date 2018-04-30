package com.mersiyanov.dmitry.yadg.mvp;

import com.mersiyanov.dmitry.yadg.network.RetroHelper;
import com.mersiyanov.dmitry.yadg.pojo.ResponseFileList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainRepo implements PicturesContract.Repo {

    private final RetroHelper retroHelper;
    private Single<ResponseFileList> cache;

    public MainRepo(RetroHelper retroHelper) {
        this.retroHelper = retroHelper;
    }


    @Override
    public Single<ResponseFileList> load() {

        if(cache == null) {
            cache = retroHelper.getApi().getImagesList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache();
        }

        return cache;
    }
}
