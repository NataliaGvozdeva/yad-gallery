package com.mersiyanov.dmitry.yadg.mvp;

import android.util.Log;

import com.mersiyanov.dmitry.yadg.pojo.ResponseFileList;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements PicturesContract.Presenter {

    private PicturesContract.View view;

    public MainPresenter(PicturesContract.Repo repo) {
        this.repo = repo;
    }

    private PicturesContract.Repo repo;
//    static public List<ResponseFileList.Item> itemList;

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

        view.showLoading();

        repo.load()
                .subscribe(new SingleObserver<ResponseFileList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseFileList responseFileList) {
//                        itemList = responseFileList.getItems();

//                        view.showData(itemList);
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
