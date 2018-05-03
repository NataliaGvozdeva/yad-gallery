package com.mersiyanov.dmitry.yadg.di;

import com.mersiyanov.dmitry.yadg.network.RetroHelper;
import com.mersiyanov.dmitry.yadg.ui.mvp.MainPresenter;
import com.mersiyanov.dmitry.yadg.ui.mvp.MainRepo;
import com.mersiyanov.dmitry.yadg.ui.mvp.PicturesContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    RetroHelper provideApi() {
        return new RetroHelper();
    }


    @Singleton
    @Provides
    PicturesContract.Repo provideRepo(RetroHelper apiHelper) {
        return new MainRepo(apiHelper);
    }

    @Provides
    PicturesContract.Presenter providePresenter(PicturesContract.Repo repo) {
        return new MainPresenter(repo);
    }

}