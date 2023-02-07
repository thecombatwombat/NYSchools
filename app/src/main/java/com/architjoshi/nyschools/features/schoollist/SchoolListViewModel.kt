package com.architjoshi.nyschools.features.schoollist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.architjoshi.nyschools.common.ui.SingleLiveEvent
import com.architjoshi.nyschools.domain.model.School
import com.architjoshi.nyschools.repository.NYSchoolsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchoolListViewModel @Inject constructor(
    private val nySchoolsRepository: NYSchoolsRepository
) : ViewModel() {
    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _state = SingleLiveEvent<State>()
    val state: LiveData<State> = _state

    /**
     * This load call here should have some caching so as to not redundantly fetch data when it
     * may not have gone stale. Keeping it simple for now.
     */
    fun load() {
        nySchoolsRepository.getSchoolList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _state.value = State.Loading
            }
            .subscribeBy(onError = {
                _state.value = State.Error
            }, onSuccess = {
                _state.value = State.Success(it)
            }).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Success(val schools: List<School>) : State()
    }
}