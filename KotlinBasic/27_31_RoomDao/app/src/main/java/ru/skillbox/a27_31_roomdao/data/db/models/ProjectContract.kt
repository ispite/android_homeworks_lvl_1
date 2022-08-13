package ru.skillbox.a27_31_roomdao.data.db.models

object ProjectContract {
    const val TABLE_NAME = "Projects"

    object Columns {
        const val ID = "id"
        const val COMPANY_ID = "company_id"
        const val PROJECT_TITLE = "project_title"
        const val DEADLINE = "deadline"
        const val REWARD = "reward"
    }
}