package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.categories

import io.reactivex.Observable
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.Resource
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.Category

interface CategoryRepository {

    fun fetchAll(): Observable<Resource<List<Category>>>

}