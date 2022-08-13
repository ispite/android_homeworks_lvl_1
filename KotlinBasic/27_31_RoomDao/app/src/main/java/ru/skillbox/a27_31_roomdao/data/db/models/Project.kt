package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ProjectContract.TABLE_NAME)
data class Project(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProjectContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = ProjectContract.Columns.COMPANY_ID)
    val companyId: Long,
//    @ColumnInfo(name = ProjectContract.Columns.PROJECT_TITLE)
//    val projectTitle: String,
    @ColumnInfo(name = ProjectContract.Columns.DEADLINE)
    val deadline: String,
    @ColumnInfo(name = ProjectContract.Columns.REWARD)
    val reward: Double
)
