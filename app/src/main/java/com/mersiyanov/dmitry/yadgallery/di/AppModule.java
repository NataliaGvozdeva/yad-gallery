package com.mersiyanov.dmitry.yadgallery.di;

import com.mersiyanov.dmitry.yadgallery.network.RetroHelper;
import com.mersiyanov.dmitry.yadgallery.ui.mvp.MainPresenter;
import com.mersiyanov.dmitry.yadgallery.ui.mvp.MainRepo;
import com.mersiyanov.dmitry.yadgallery.ui.mvp.PicturesContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module()
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