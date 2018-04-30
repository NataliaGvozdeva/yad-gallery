package com.mersiyanov.dmitry.yadg.mvp;

import com.mersiyanov.dmitry.yadg.pojo.ResponseFileList;

import java.util.List;

import io.reactivex.Single;

public class PicturesContract {


    interface View {

        void showLoading();

        void showError();

        void showData(List<ResponseFileList.Item> itemlist);
    }

    public interface Presenter {

        void attachView(View view);

        void detachView();

        void load();
    }

    public interface Repo {

        Single<ResponseFileList> load();
    }
}
