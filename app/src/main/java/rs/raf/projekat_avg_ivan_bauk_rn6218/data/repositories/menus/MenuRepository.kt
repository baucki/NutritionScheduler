package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.menus

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu

interface MenuRepository {

    fun getAll(): Observable<List<Menu>>
    fun insert(menu: Menu): Completable
    fun delete(id: String): Completable
    fun getAllMenusByCategory(category: String): Observable<List<Menu>>

}