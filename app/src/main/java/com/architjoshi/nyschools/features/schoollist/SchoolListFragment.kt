package com.architjoshi.nyschools.features.schoollist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.architjoshi.nyschools.NYSchoolsApplication
import com.architjoshi.nyschools.common.BaseFragment
import com.architjoshi.nyschools.databinding.FragmentSchoolListBinding

class SchoolListFragment : BaseFragment<SchoolListViewModel>() {

    private val viewModel: SchoolListViewModel by lazy {
        viewModelFactory.get<SchoolListViewModel>(requireActivity())
    }

    private lateinit var schoolListAdapter: SchoolListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NYSchoolsApplication.getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSchoolListBinding.inflate(inflater)

        schoolListAdapter = SchoolListAdapter {
            findNavController().navigate(SchoolListFragmentDirections.goToSchoolResults(it.id, it.name))
        }

        binding.schoolsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = schoolListAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is SchoolListViewModel.State.Error -> {
                    dismissLoadingDialog()
                    // show error UI
                    Toast.makeText(
                        context,
                        "There was an error loading data. Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is SchoolListViewModel.State.Loading -> {
                    showLoadingDialog()
                }
                is SchoolListViewModel.State.Success -> {
                    dismissLoadingDialog()
                    schoolListAdapter.schools = it.schools
                }
            }
        }

        viewModel.load()
    }

    override fun onPause() {
        super.onPause()
        viewModel.state.removeObservers(viewLifecycleOwner)
    }
}