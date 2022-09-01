package com.osvaldo.stickerstracker.appDatabase

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.osvaldo.stickerstracker.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Nation::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3, spec = NationsDatabase.NationsMigration::class)
    ]
)
@TypeConverters(PlayerTypeConverter::class)
abstract class NationsDatabase : RoomDatabase() {
    abstract val nationDao: NationDao

    @DeleteColumn(tableName = "NationsTable", columnName = "nationFlag")
    class NationsMigration : AutoMigrationSpec

    class NationCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch {
                NationEnum.values().forEach {
                    INSTANCE?.insert(
                        Nation(
                            nationName = it.getName(),
                            nationEnum = it,
                            listOfPlayers = it.generateListOfPlayers()
                        )
                    )
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NationDao? = null

        fun getDao(
            context: Context,
            scope: CoroutineScope
        ): NationDao {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NationsDatabase::class.java,
                    "nation_database"
                )
                    .addCallback(NationCallback(scope))
                    .build().nationDao
                INSTANCE = instance
                instance
            }
        }
    }
}