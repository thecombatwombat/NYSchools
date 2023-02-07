package com.architjoshi.nyschools.utils.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.architjoshi.nyschools.network.api.NYSchoolsAPIService
import com.architjoshi.nyschools.repository.NYCSchoolRepositoryImpl
import com.architjoshi.nyschools.repository.NYSchoolsRepository
import com.architjoshi.nyschools.utils.RxJavaRule
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.architjoshi.nyschools.domain.model.School as SchoolDomainModel
import com.architjoshi.nyschools.domain.model.SchoolSATResults as SchoolSATResultsDomainModel
import com.architjoshi.nyschools.network.model.School as SchoolNetworkModel
import com.architjoshi.nyschools.network.model.SchoolSATResults as SchoolSATResultsNetworkModel

@RunWith(JUnit4::class)
class NYSchoolsRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxJavaRule = RxJavaRule()

    private val apiService: NYSchoolsAPIService = mockk()
    private lateinit var repository: NYSchoolsRepository

    @Before
    fun setup() {
        every { apiService.getSchoolList() } returns Single.just(listOf(schoolNetworkModel))
        every { apiService.getSATResultsForSchool(any()) } returns Single.just(
            listOf(
                schoolSATResultsNetworkModel
            )
        )

        repository = NYCSchoolRepositoryImpl(apiService)
    }

    @Test
    fun `fetching schools list maps to domain models`() {
        // Given
        val testObserver = TestObserver<List<SchoolDomainModel>>()

        // Execute
        val result = repository.getSchoolList()
        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val list = testObserver.values()[0]
        assert(list[0] == schoolDomainModel)
    }

    @Test
    fun `fetching school sat results maps to domain models`() {
        // Given
        val testObserver = TestObserver<List<SchoolSATResultsDomainModel>>()

        // Execute
        val result = repository.getSchoolSATResults(id)
        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val schoolSATResults = testObserver.values()[0][0]
        with(schoolSATResults) {
            assert(schoolId == id)
            assert(name == schoolName)
            assert(readingScore == reading)
            assert(mathScore == math)
            assert(writingScore == writing)
        }
    }

    companion object {
        private const val id = "1"
        private const val schoolName = "name"
        private const val reading = "reading_score"
        private const val math = "math_score"
        private const val writing = "writing_score"

        private val schoolDomainModel = SchoolDomainModel(
            id = id,
            name = schoolName,
            neighborhood = "neighborhood1",
            city = "city1"
        )

        private val schoolNetworkModel = SchoolNetworkModel(
            id = id,
            name = schoolName,
            neighborhood = "neighborhood1",
            city = "city1"
        )

        private val schoolSATResultsNetworkModel = SchoolSATResultsNetworkModel(
            id,
            schoolName,
            "100",
            reading,
            math,
            writing
        )

        private val schoolSATResultsDomainModel = SchoolSATResultsDomainModel(
            id,
            schoolName,
            reading,
            math,
            writing
        )
    }
}