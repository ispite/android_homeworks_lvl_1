package ru.skillbox.a27_31_roomdao.ui.employees.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a27_31_roomdao.data.db.models.Employee

class EmployeeListAdapter(
    onEmployeeClick: (Employee) -> Unit,
    onDeleteEmployee: (Employee) -> Unit
) : AsyncListDifferDelegationAdapter<Employee>(EmployeeDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(EmployeeAdapterDelegate(onEmployeeClick, onDeleteEmployee))
    }

    class EmployeeDiffUtilCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }
}