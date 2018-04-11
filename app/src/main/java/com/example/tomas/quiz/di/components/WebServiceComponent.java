package com.example.tomas.quiz.di.components;

import com.example.tomas.quiz.activities.MainActivity;
import com.example.tomas.quiz.di.providers.WebServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tomas on 11.04.2018.
 *
 * Component telling which classes have access to WebServiceModule.
 */

@Singleton
@Component(modules = WebServiceModule.class)
public interface WebServiceComponent {

    void inject(MainActivity mainActivity);
}
