package ru.skillbox.a27_31_roomdao.ui.employees.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_employee.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.utils.inflate

class EmployeeAdapterDelegate(
    private val onEmployeeClick: (Employee) -> Unit,
    private val onDeleteEmployee: (Employee) -> Unit
) : AbsListItemAdapterDelegate<Employee, Employee, EmployeeAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Employee,
        items: MutableList<Employee>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return parent.inflate(R.layout.item_employee).let {
            Holder(it, onEmployeeClick, onDeleteEmployee)
        }
    }

    override fun onBindViewHolder(item: Employee, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        onEmployeeClick: (Employee) -> Unit,
        onDeleteEmployee: (Employee) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var currentEmployee: Employee? = null

        init {
            containerView.setOnClickListener { currentEmployee?.let(onEmployeeClick) }
            deleteEmployeeButton.setOnClickListener { currentEmployee?.let(onDeleteEmployee) }
        }

        fun bind(employee: Employee) {
            currentEmployee = employee
            idTextView.text = employee.id.toString()
            companyIdTextView.text = employee.companyId.toString()
            firstNameTextView.text = employee.firstName
            lastNameTextView.text = employee.lastName
            birthdateTextView.text = employee.birthdate
        }
    }
}