package com.architjoshi.nyschools.utils.features.schoollist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.architjoshi.nyschools.domain.model.School
import com.architjoshi.nyschools.features.schoollist.SchoolListViewModel
import com.architjoshi.nyschools.features.schoollist.SchoolListViewModel.State
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
class SchoolListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxJavaRule = RxJavaRule()

    private val nySchoolsRepository: NYSchoolsRepository = mockk()
    private val mockObserver: Observer<State> = mockk(relaxed = true)

    @Before
    fun setup() {
        every { nySchoolsRepository.getSchoolList() } returns Single.just(schools)
    }

    @Test
    fun `on load fetches data`() {
        // Given
        val viewModel = SchoolListViewModel(nySchoolsRepository)

        // Execute
        viewModel.load()

        // Verify
        verify { nySchoolsRepository.getSchoolList() }
    }

    @Test
    fun `on error propagates error state`() {
        // Given
        every { nySchoolsRepository.getSchoolList() } returns Single.error(Throwable())
        val viewModel = SchoolListViewModel(nySchoolsRepository)
        viewModel.state.observeForever(mockObserver)

        // Execute
        viewModel.load()

        // Verify
        verifySequence {
            mockObserver.onChanged(State.Loading)
            mockObserver.onChanged(State.Error)
        }
    }

    @Test
    fun `on success propagates success state with school data`() {
        val viewModel = SchoolListViewModel(nySchoolsRepository)
        viewModel.state.observeForever(mockObserver)

        // Execute
        viewModel.load()

        // Verify
        verifySequence {
            mockObserver.onChanged(State.Loading)
            mockObserver.onChanged(State.Success(schools))
        }
    }

    companion object {
        private val schools = listOf(
            School(
                id = "1",
                name = "name1",
                neighborhood = "neighborhood1",
                city = "city1"
            ),
            School(
                id = "2",
                name = "name2",
                neighborhood = "neighborhood2",
                city = "city2"
            )
        )
    }
}