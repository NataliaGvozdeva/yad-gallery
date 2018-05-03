package com.mersiyanov.dmitry.yadg.di;

import com.mersiyanov.dmitry.yadg.ui.mvp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void injects(MainActivity target);
}


