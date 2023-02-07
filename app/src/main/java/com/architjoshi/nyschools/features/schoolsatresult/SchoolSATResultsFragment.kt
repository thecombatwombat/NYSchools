package com.architjoshi.nyschools.features.schoolsatresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.architjoshi.nyschools.NYSchoolsApplication
import com.architjoshi.nyschools.common.BaseFragment
import com.architjoshi.nyschools.databinding.FragmentSchoolSatResultBinding


class SchoolSATResultsFragment : BaseFragment<SchoolSATResultsViewModel>() {

    private val viewModel: SchoolSATResultsViewModel by lazy {
        viewModelFactory.get<SchoolSATResultsViewModel>(requireActivity())
    }

    private lateinit var binding: FragmentSchoolSatResultBinding
    private val args: SchoolSATResultsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NYSchoolsApplication.getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSchoolSatResultBinding.inflate(inflater)
            .apply { lifecycleOwner = viewLifecycleOwner }
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                SchoolSATResultsViewModel.State.Loading -> {
                    showLoadingDialog()
                }
                SchoolSATResultsViewModel.State.Error -> {
                    dismissLoadingDialog()

                    // show error UI
                    Toast.makeText(
                        context,
                        "There was an error loading data. Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }
                SchoolSATResultsViewModel.State.Success -> {
                    dismissLoadingDialog()

                    toggleNoDataVisibility(false)
                    toggleScoreVisibility(true)
                }
                SchoolSATResultsViewModel.State.NoDataAvailable -> {
                    dismissLoadingDialog()

                    toggleNoDataVisibility(true)
                    toggleScoreVisibility(false)
                }
            }
        }

        viewModel.load(args.schoolId, args.schoolName)
    }

    private fun toggleScoreVisibility(visible: Boolean) {
        binding.apply {
            readingScore.visibility = if (visible) View.VISIBLE else View.GONE
            mathScore.visibility = if (visible) View.VISIBLE else View.GONE
            writingScore.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

    private fun toggleNoDataVisibility(visible: Boolean) {
        binding.noDataAvailable.visibility = if (visible) View.VISIBLE else View.GONE
    }
}