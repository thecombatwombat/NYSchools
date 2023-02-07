package com.architjoshi.nyschools.network.api

import com.architjoshi.nyschools.common.Constants
import com.architjoshi.nyschools.network.model.School
import com.architjoshi.nyschools.network.model.SchoolSATResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NYSchoolsAPIService {

    /**
     * Returns a list of NYC public schools
     */
    @GET(Constants.SCHOOL_LIST_ENDPOINT)
    fun getSchoolList(): Single<List<School>>

    /**
     * Returns SAT results of a specified school
     */
    @GET(Constants.SAT_RESULTS_ENDPOINT)
    fun getSATResultsForSchool(@Query("dbn") schoolId: String): Single<List<SchoolSATResults>>
}