package rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.MenuEntity

@Database(
    entities = [MenuEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters()
abstract class DataBase : RoomDatabase() {
    abstract fun getMenuDao(): MenuDao
}