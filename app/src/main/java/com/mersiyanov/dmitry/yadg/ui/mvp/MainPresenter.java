package com.mersiyanov.dmitry.yadg.ui.mvp;

import android.util.Log;

import com.mersiyanov.dmitry.yadg.pojo.ResponseFileList;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements PicturesContract.Presenter {

    private PicturesContract.View view;
    private PicturesContract.Repo repo;

    public MainPresenter(PicturesContract.Repo repo) {
        this.repo = repo;
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
    public void load() {

        view.showLoading();
        repo.load().subscribe(new SingleObserver<ResponseFileList>() {
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
