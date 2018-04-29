package com.mersiyanov.dmitry.yadg.mvp;

import android.util.Log;

import com.mersiyanov.dmitry.yadg.ResponseFileList;
import com.mersiyanov.dmitry.yadg.network.RetroHelper;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements PicturesContract.Presenter {

    private PicturesContract.View view;

    @Override
    public void attachView(PicturesContract.View view) {
        this.view = view;

    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void load() {
        RetroHelper.getApi().getImagesList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFileList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseFileList responseFileList) {
//                        itemList = responseFileList.getItems();

                        view.showLoading();
                        view.showData(responseFileList.getItems());





                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getLocalizedMessage());
                        view.showError();

                    }
                });

    }
}
