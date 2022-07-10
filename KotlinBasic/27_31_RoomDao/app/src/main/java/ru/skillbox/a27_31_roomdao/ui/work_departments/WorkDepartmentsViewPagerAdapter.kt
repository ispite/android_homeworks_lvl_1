package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_page.view.*
import ru.skillbox.a27_31_roomdao.R

class WorkDepartmentsViewPagerAdapter :
    RecyclerView.Adapter<WorkDepartmentsViewPagerAdapter.WorkDepartmentsHolder>() {

    private val colors = intArrayOf(
        android.R.color.black,
        android.R.color.holo_red_light,
        android.R.color.holo_blue_dark,
        android.R.color.holo_purple
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkDepartmentsHolder {
        return WorkDepartmentsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WorkDepartmentsHolder, position: Int) {
        holder.itemView.run {
            departmentPositions.text = "item $position"
            container.setBackgroundResource(colors[position])
        }
    }

    override fun getItemCount(): Int = colors.size

    class WorkDepartmentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
    }
}