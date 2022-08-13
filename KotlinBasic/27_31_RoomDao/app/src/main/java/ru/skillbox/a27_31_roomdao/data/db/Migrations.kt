package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import timber.log.Timber

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        Timber.d("migration 1-2 start")
        database.execSQL("CREATE TABLE IF NOT EXISTS `Projects` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `deadline` TEXT NOT NULL, `reward` REAL NOT NULL)")
        Timber.d("migration 1-2 success")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        Timber.d("migration 2-3 start")
        database.execSQL("ALTER TABLE `Projects` ADD COLUMN `company_id` INTEGER NOT NULL DEFAULT 0")
        Timber.d("migration 2-3 success")
    }
}