package com.architjoshi.nyschools.utils.features.schoolsatresult

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.architjoshi.nyschools.domain.model.SchoolSATResults
import com.architjoshi.nyschools.features.schoolsatresult.SchoolSATResultsViewModel
import com.architjoshi.nyschools.features.schoolsatresult.SchoolSATResultsViewModel.State
import com.architjoshi.nyschools.repository.NYSchoolsRepository
import com.architjoshi.nyschools.utils.RxJavaRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SchoolSATResultsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxJavaRule = RxJavaRule()

    private val nySchoolsRepository: NYSchoolsRepository = mockk()
    private val schoolNameObserver: Observer<String> = mockk(relaxed = true)
    private val resultsObserver: Observer<SchoolSATResults> = mockk(relaxed = true)
    private val stateObserver: Observer<State> = mockk(relaxed = true)

    @Before
    fun setup() {
        every { nySchoolsRepository.getSchoolSATResults(any()) } returns Single.just(
            listOf(
                schoolSATResults
            )
        )
    }

    @Test
    fun `on load fetches data and displays name`() {
        // Given
        val viewModel = SchoolSATResultsViewModel(nySchoolsRepository)
        viewModel.schoolName.observeForever(schoolNameObserver)
        viewModel.state.observeForever(stateObserver)

        // Execute
        viewModel.load(schoolId, schoolName)

        // Verify
        verify { nySchoolsRepository.getSchoolSATResults(schoolId) }
        verify { schoolNameObserver.onChanged(schoolName) }
    }

    @Test
    fun `on error propagates error state`() {
        // Given
        every { nySchoolsRepository.getSchoolSATResults(any()) } returns Single.error(Throwable())
        val viewModel = SchoolSATResultsViewModel(nySchoolsRepository)
        viewModel.state.observeForever(stateObserver)

        // Execute
        viewModel.load(schoolId, schoolName)

        // Verify
        verifySequence {
            stateObserver.onChanged(State.Loading)
            stateObserver.onChanged(State.Error)
        }
    }

    @Test
    fun `on success with results present propagates success state with school result data`() {
        // Given
        val viewModel = SchoolSATResultsViewModel(nySchoolsRepository)
        viewModel.state.observeForever(stateObserver)
        viewModel.results.observeForever(resultsObserver)

        // Execute
        viewModel.load(schoolId, schoolName)

        // Verify
        verifySequence {
            stateObserver.onChanged(State.Loading)
            stateObserver.onChanged(State.Success)
        }

        verify { resultsObserver.onChanged(schoolSATResults) }
    }

    @Test
    fun `on success with no results present propagates no data available state `() {
        // Given
        every { nySchoolsRepository.getSchoolSATResults(any()) } returns Single.just(emptyList())
        val viewModel = SchoolSATResultsViewModel(nySchoolsRepository)
        viewModel.state.observeForever(stateObserver)

        // Execute
        viewModel.load(schoolId, schoolName)

        // Verify
        verifySequence {
            stateObserver.onChanged(State.Loading)
            stateObserver.onChanged(State.NoDataAvailable)
        }
    }

    companion object {
        private const val schoolId = "1"
        private const val schoolName = "name"
        private val schoolSATResults = SchoolSATResults(
            schoolId,
            schoolName,
            "reading_score",
            "math_score",
            "writing_score"
        )
    }
}