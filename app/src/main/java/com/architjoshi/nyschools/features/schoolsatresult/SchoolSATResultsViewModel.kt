package com.architjoshi.nyschools.features.schoolsatresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.architjoshi.nyschools.common.ui.SingleLiveEvent
import com.architjoshi.nyschools.domain.model.SchoolSATResults
import com.architjoshi.nyschools.repository.NYSchoolsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchoolSATResultsViewModel @Inject constructor(
    private val nySchoolsRepository: NYSchoolsRepository
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _state = SingleLiveEvent<State>()
    val state: LiveData<State> = _state

    // Normally I would use a dedicated model to pass to data binding, but given limited time
    // for now I am just passing the domain model
    private val _results = MutableLiveData<SchoolSATResults>()
    val results = _results

    private val _schoolName = MutableLiveData<String>()
    val schoolName: LiveData<String> = _schoolName

    fun load(
        schoolId: String,
        schoolName: String
    ) {
        _schoolName.value = schoolName
        nySchoolsRepository.getSchoolSATResults(schoolId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                _state.value = State.Loading
            }.subscribe(
                {
                    val result = it.firstOrNull()
                    if (result == null) {
                        _state.value = State.NoDataAvailable
                    } else {
                        _results.value = result!!
                        _state.value = State.Success
                    }
                }, {
                    _state.value = State.Error
                }).addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    sealed class State {
        object Loading : State()
        object Error : State()
        object Success : State()
        object NoDataAvailable : State()
    }
}