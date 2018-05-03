package com.mersiyanov.dmitry.yadg.ui.mvp;

import android.content.Context;
import android.util.Log;

import com.mersiyanov.dmitry.yadg.network.NetworkUtils;
import com.mersiyanov.dmitry.yadg.pojo.ResponseFileList;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements PicturesContract.Presenter {

    private PicturesContract.View view;
    private PicturesContract.Repo repo;

    public MainPresenter(PicturesContract.Repo repo) {
        this.repo = repo;
//        this.context = context;
    }

    @Override
    public void attachView(PicturesContract.View view) {
        this.view = view;
        repo.initDB();
    }

    @Override
    public void detachView() {
        view = null;
        repo.closeDB();
    }

    @Override
    public void getPictures(String token) {
        Context context = ((MainActivity) view).getApplicationContext();

        if(NetworkUtils.isNetworkAvailable(context)) {
            load(token);

        } else {
            view.showError();
        }
    }

    private void load(String token) {
        view.showLoading();

        repo.load(token).subscribe(new SingleObserver<ResponseFileList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseFileList responseFileList) {
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
