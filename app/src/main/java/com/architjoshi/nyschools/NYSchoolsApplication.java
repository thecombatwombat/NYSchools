package com.architjoshi.nyschools;

import android.app.Application;

import androidx.annotation.NonNull;

import com.architjoshi.nyschools.dagger.AppComponent;
import com.architjoshi.nyschools.dagger.AppModule;
import com.architjoshi.nyschools.dagger.DaggerAppComponent;

public class NYSchoolsApplication extends Application {

    private static AppComponent appComponent;

    public static @NonNull AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerAppComponent();
    }

    private void initDaggerAppComponent() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
