package com.architjoshi.nyschools.dagger

import com.architjoshi.nyschools.NYSchoolsApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val application: NYSchoolsApplication) {

    @Provides
    @Singleton
    fun getApplication(): NYSchoolsApplication {
        return application
    }
}