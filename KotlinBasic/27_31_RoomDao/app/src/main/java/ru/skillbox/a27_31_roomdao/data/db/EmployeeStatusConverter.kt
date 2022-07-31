package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.TypeConverter
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeeStatus

class EmployeeStatusConverter {

    @TypeConverter
    fun convertEmployeeStatusToString(status: EmployeeStatus): String = status.name

    @TypeConverter
    fun convertStringToEmployeeStatus(statusString: String): EmployeeStatus =
        EmployeeStatus.valueOf(statusString)
}