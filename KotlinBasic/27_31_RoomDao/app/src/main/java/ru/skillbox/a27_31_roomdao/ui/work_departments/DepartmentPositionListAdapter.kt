package ru.skillbox.a27_31_roomdao.ui.work_departments

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition

class DepartmentPositionListAdapter(
    onDepartmentPositionClick: (DepartmentPosition) -> Unit,
    onDeleteDepartmentPosition: (DepartmentPosition) -> Unit
) : AsyncListDifferDelegationAdapter<DepartmentPosition>(DepartmentPositionDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(DepartmentPositionDelegate(onDepartmentPositionClick, onDeleteDepartmentPosition))
    }

    class DepartmentPositionDiffUtilCallback : DiffUtil.ItemCallback<DepartmentPosition>() {
        override fun areItemsTheSame(
            oldItem: DepartmentPosition,
            newItem: DepartmentPosition
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DepartmentPosition,
            newItem: DepartmentPosition
        ): Boolean {
            return oldItem == newItem
        }
    }
}