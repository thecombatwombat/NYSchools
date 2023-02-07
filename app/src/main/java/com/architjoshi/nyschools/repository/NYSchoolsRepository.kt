package com.architjoshi.nyschools.repository

import com.architjoshi.nyschools.domain.model.School
import com.architjoshi.nyschools.domain.model.SchoolSATResults
import io.reactivex.Single

/**
 * Repository to manage interactions with the network and any possible future persistence layers
 */
interface NYSchoolsRepository {
    /**
     * Get the list of schools in NYC, mapped from network model to domain model
     */
    fun getSchoolList(): Single<List<School>>

    /**
     * Get the SAT results for the specified school, mapped from network model to domain model
     */
    fun getSchoolSATResults(schoolId: String): Single<List<SchoolSATResults>>
}