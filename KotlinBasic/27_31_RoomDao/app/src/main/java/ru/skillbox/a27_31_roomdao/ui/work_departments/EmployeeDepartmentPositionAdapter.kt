package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeeDepartmentPosition

class EmployeeDepartmentPositionAdapter :
    RecyclerView.Adapter<EmployeeDepartmentPositionAdapter.EmployeeDepartmentPositionViewHolder>() {

    private var employeeDepartmentPositionListPair:
            List<Pair<EmployeeDepartmentPosition, Employee>> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeDepartmentPositionViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_employee_department_position, parent, false)
        return EmployeeDepartmentPositionViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: EmployeeDepartmentPositionViewHolder, position: Int) {
        val currentEmployeeDepartmentPosition = employeeDepartmentPositionListPair[position]
        holder.bind(currentEmployeeDepartmentPosition)
    }

    override fun getItemCount(): Int = employeeDepartmentPositionListPair.size

    fun addData(employeeDepartmentPositionInput: List<Pair<EmployeeDepartmentPosition, Employee>>) {
        employeeDepartmentPositionListPair = employeeDepartmentPositionInput
        notifyDataSetChanged()
    }

    class EmployeeDepartmentPositionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val idTextView: TextView = view.findViewById(R.id.idTextView)
        private val employeeIdTextView: TextView = view.findViewById(R.id.employeeIdTextView)
        private val departmentPositionIdTextView: TextView =
            view.findViewById(R.id.departmentPositionIdTextView)
        private val firstNameTextView: TextView = view.findViewById(R.id.firstNameTextView)
        private val lastNameTextView: TextView = view.findViewById(R.id.lastNameTextView)
        private val birthdateTextView: TextView = view.findViewById(R.id.birthdateTextView)

        fun bind(item: Pair<EmployeeDepartmentPosition, Employee>) {
            idTextView.text = item.first.id.toString()
            employeeIdTextView.text = item.first.employee_id.toString()
            departmentPositionIdTextView.text =
                item.first.departmentPosition_id.toString()
            firstNameTextView.text = item.second.firstName
            lastNameTextView.text = item.second.lastName
            birthdateTextView.text = item.second.birthdate
        }
    }
}