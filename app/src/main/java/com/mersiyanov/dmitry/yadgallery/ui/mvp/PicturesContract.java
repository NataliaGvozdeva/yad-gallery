package com.mersiyanov.dmitry.yadgallery.ui.mvp;

import com.mersiyanov.dmitry.yadgallery.pojo.Item;
import com.mersiyanov.dmitry.yadgallery.pojo.ResponseFileList;

import java.util.List;

import io.reactivex.Single;

public class PicturesContract {

    public interface View {

        void showLoading();

        void showError();

        void showData(List<Item> itemlist);
    }

    public interface Presenter {

        void attachView(View view);

        void detachView();

        void getPictures(String token);
    }

    public interface Repo {

        Single<ResponseFileList> load(String token);

        void initDB();

        void closeDB();
    }
}
