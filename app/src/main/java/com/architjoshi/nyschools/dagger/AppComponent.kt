package com.architjoshi.nyschools.dagger

import com.architjoshi.nyschools.NYSchoolsApplication
import com.architjoshi.nyschools.features.schoollist.SchoolListFragment
import com.architjoshi.nyschools.features.schoolsatresult.SchoolSATResultsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: NYSchoolsApplication)
    fun inject(schoolListFragment: SchoolListFragment)
    fun inject(schoolSATResultsFragment: SchoolSATResultsFragment)
}