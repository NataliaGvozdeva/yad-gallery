package com.mersiyanov.dmitry.yadgallery.di;

import com.mersiyanov.dmitry.yadgallery.ui.mvp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent  {

    void injects(MainActivity target);
}


