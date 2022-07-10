package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_work_departments.*
import kotlinx.coroutines.launch
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment

class WorkDepartmentsFragment : Fragment(R.layout.fragment_work_departments) {

    private val workDepartmentDao = Database.instance.workDepartmentDao()
    private val departmentPositionDao = Database.instance.departmentPositionDao()

    lateinit var listWorkDepartment: List<WorkDepartment>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveWorkDepartments()
        saveDepartmentPositions()
        createTabs()

//        workDepartmentViewPager.adapter = WorkDepartmentsViewPagerAdapter()

        workDepartmentViewPager.adapter = WorkDepartmentsFragmentStateAdapter(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setCo
//        createTabs()
    }

    private fun saveWorkDepartments() {
//        Timber.d("saveWorkDepartments")
        val workDepartments = listOf(
            WorkDepartment(
                id = 0,
                companyId = 0,
                workDepartmentName = "Производство"
            ),
            WorkDepartment(
                id = 0,
                companyId = 0,
                workDepartmentName = "Бухгалтерия"
            ),
            WorkDepartment(
                id = 0,
                companyId = 0,
                workDepartmentName = "АСУ ТП"
            )
        )
        lifecycleScope.launch {
            workDepartmentDao.insertWorkDepartment(workDepartments)
        }
    }

    private fun saveDepartmentPositions() {
        val departmentPosition = listOf(
            DepartmentPosition(
                id = 0,
                workDepartmentId = 1,
                jobTitle = "Слесарь",
                salary = 50000.00
            ),
            DepartmentPosition(
                id = 0,
                workDepartmentId = 1,
                jobTitle = "Токарь",
                salary = 65000.00
            ),
            DepartmentPosition(
                id = 0,
                workDepartmentId = 2,
                jobTitle = "Бухгалтер",
                salary = 50000.00
            ),
            DepartmentPosition(
                id = 0,
                workDepartmentId = 3,
                jobTitle = "Инженер АСУ ТП",
                salary = 50000.00
            )
        )
        lifecycleScope.launch {
            departmentPositionDao.insertDepartmentPosition(departmentPosition)
        }
    }

    fun createTabs() {
        val tabs = listOf(1, 2, 3)

        val adapter = TabLayoutAdapter(tabs, requireActivity())
        workDepartmentViewPager.adapter = adapter
        val tabNames = listOf(
            "1",
            "2",
            "3"
        )
//        tabLayout.setupWithViewPager(workDepartmentViewPager)
        TabLayoutMediator(tabLayout, workDepartmentViewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }
}