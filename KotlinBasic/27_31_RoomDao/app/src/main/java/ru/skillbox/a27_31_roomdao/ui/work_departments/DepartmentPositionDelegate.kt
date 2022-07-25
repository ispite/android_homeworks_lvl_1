package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_department_position.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import ru.skillbox.a27_31_roomdao.utils.inflate

class DepartmentPositionDelegate(
    private val onDepartmentPositionClick: (DepartmentPosition) -> Unit,
    private val onDeleteDepartmentPosition: (DepartmentPosition) -> Unit
) : AbsListItemAdapterDelegate<DepartmentPosition, DepartmentPosition, DepartmentPositionDelegate.Holder>() {

    override fun isForViewType(
        item: DepartmentPosition,
        items: MutableList<DepartmentPosition>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return parent.inflate(R.layout.item_department_position).let {
            Holder(it, onDepartmentPositionClick, onDeleteDepartmentPosition)
        }
    }

    override fun onBindViewHolder(
        item: DepartmentPosition,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        onDepartmentPositionClick: (DepartmentPosition) -> Unit,
        onDeleteDepartmentPosition: (DepartmentPosition) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var currentDepartmentPosition: DepartmentPosition? = null

        init {
            containerView.setOnClickListener {
                currentDepartmentPosition?.let(
                    onDepartmentPositionClick
                )
            }
            deleteDepartmentPositionButton.setOnClickListener {
                currentDepartmentPosition?.let(
                    onDeleteDepartmentPosition
                )
            }
        }

        fun bind(departmentPosition: DepartmentPosition) {
            currentDepartmentPosition = departmentPosition
            idTextView.text = departmentPosition.id.toString()
            workDepartmentIdTextView.text = departmentPosition.workDepartmentId.toString()
            jobTitleTextView.text = departmentPosition.jobTitle
            salaryTextView.text = departmentPosition.salary.toString()
        }
    }

}