package rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.MenuEntity

@Dao
abstract class MenuDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MenuEntity): Completable

    @Query("SELECT * FROM menu")
    abstract fun getAll(): Observable<List<MenuEntity>>

    @Query("DELETE FROM menu")
    abstract fun deleteAll()

    @Query("DELETE FROM menu WHERE idMenu LIKE :id || '%'")
    abstract fun delete(id: String): Completable

    @Query("SELECT * FROM menu WHERE strCategory LIKE :category || '%'")
    abstract fun getByCategory(category: String): Observable<List<MenuEntity>>

}