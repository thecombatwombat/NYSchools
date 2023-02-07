package com.architjoshi.nyschools.features.schoollist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architjoshi.nyschools.databinding.SchoolItemViewBinding
import com.architjoshi.nyschools.domain.model.School

/**
 * For large lists, it makes more sense to use a Paginated adapter, but for expediencies sake
 * I am just using a normal adapter without any diffing logic, and calling notifyDataSetChanged
 */
class SchoolListAdapter(
    private val onSchoolClick: (school: School) -> Unit
) : RecyclerView.Adapter<SchoolItemViewHolder>() {

    var schools: List<School> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolItemViewHolder {
        val binding = SchoolItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SchoolItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SchoolItemViewHolder, position: Int) {
        val school = schools[position]

        // Normally I would use a dedicated model to pass to data binding, but given limited time
        // for now I am just passing the domain model
        holder.binding.school = school
        holder.binding.root.setOnClickListener { onSchoolClick.invoke(school) }
    }

    override fun getItemCount() = schools.size
}

data class SchoolItemViewHolder(val binding: SchoolItemViewBinding) :
    RecyclerView.ViewHolder(binding.root)