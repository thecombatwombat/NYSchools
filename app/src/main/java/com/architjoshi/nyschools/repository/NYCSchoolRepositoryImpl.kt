package com.architjoshi.nyschools.repository

import com.architjoshi.nyschools.domain.model.School
import com.architjoshi.nyschools.domain.model.SchoolSATResults
import com.architjoshi.nyschools.network.api.NYSchoolsAPIService
import io.reactivex.Single

/**
 * Implementation of [NYSchoolsRepository], responsible for fetching data from the
 * [NYSchoolsAPIService] and mapping responses from network models to domain models
 *
 * Normally the mapping of domain models to network models would live in a separate class
 * for better separation of concerns and testing, but in the interest of time for this project,
 * this class handles the mapping
 */
class NYCSchoolRepositoryImpl(
    private val nySchoolsAPIService: NYSchoolsAPIService
) : NYSchoolsRepository {
    override fun getSchoolList(): Single<List<School>> {
        return nySchoolsAPIService
            .getSchoolList()
            .map { schools ->
                schools.map {
                    School(
                        id = it.id,
                        name = it.name,
                        neighborhood = it.neighborhood,
                        city = it.city
                    )
                }
            }
    }

    override fun getSchoolSATResults(schoolId: String): Single<List<SchoolSATResults>> {
        return nySchoolsAPIService
            .getSATResultsForSchool(schoolId = schoolId)
            .map {
                it.map { results ->
                    SchoolSATResults(
                        results.schoolId,
                        results.name,
                        results.readingScore,
                        results.mathScore,
                        results.writingScore
                    )
                }
            }
    }
}